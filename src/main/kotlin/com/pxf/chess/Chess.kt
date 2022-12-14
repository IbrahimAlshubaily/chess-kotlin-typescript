package com.pxf.chess

import com.pxf.chess.ChessPieces.*
class Chess {
    private var pieces: HashMap<Position, ChessPiece> = initPieces()
    private fun initPieces(): HashMap<Position, ChessPiece> {
        return HashMap(
            initTeam(Team.WHITE, 0, 1)
            .plus(initTeam(Team.BLACK, 7, 6))
            .toSortedMap(compareBy<Position> { it.row }.thenBy { it.col }))
    }

    private fun initTeam(team: Team, mainRow: Int, pawnRow: Int): MutableMap<Position, ChessPiece> {
        val out = mutableMapOf<Position, ChessPiece>()
        for ((col, piece) in listOf(ROOK, BISHOP, KNIGHT, QUEEN, KING, KNIGHT, BISHOP, ROOK).withIndex()){
            out[Position(mainRow, col)] = ChessPiece.chessPieceFactory(piece, team)
            out[Position(pawnRow, col)] = Pawn(team)
        }
        return out
    }

    override fun toString(): String {
        val out = Array(8) { Array<String>(8) { " --  " } }
        pieces.forEach {
            out[it.key.row][it.key.col] = it.value.toString()
        }
        return out.joinToString(separator = "\n") { it.contentToString() }
    }

    fun movePiece(move: Move): Boolean {
        val piece = pieces[move.origin]
        if (piece != null && piece.getMoves(pieces).contains(move.destination)) {
            pieces.remove(move.origin)
            pieces[move.destination] = piece
            promotePawn(pieces, move.destination)
            return true
        }
        return false
    }

    fun minMaxStep(): Move{
        val nextPieces = MinMax.getBestMoves(pieces)
        val prevPos = pieces.keys.toSet().subtract(nextPieces.keys.toSet())
        val newPos = nextPieces.keys.toSet().subtract(pieces.keys.toSet())
        println(prevPos.first())
        println(newPos.first())
        pieces = nextPieces
        return Move(prevPos.first(), newPos.first())
    }

    fun getMoves(position: Position) =
        if (pieces[position]?.team == Team.BLACK) pieces[position]?.getMoves(pieces)
        else emptyList()

    fun getPieces():Map<Position, ChessPiece> = pieces

    companion object {
        fun makeMove(originalPieces: HashMap<Position, ChessPiece>, move: Move): HashMap<Position, ChessPiece> {
            val pieces = HashMap(originalPieces)
            pieces[move.destination] = pieces.remove(move.origin)
            promotePawn(pieces, move.destination)
            return pieces
        }

        fun promotePawn(pieces: HashMap<Position, ChessPiece>, position: Position){
            val piece = pieces[position]
            if(piece != null && piece.repr.lowercase() == "p" &&
                ((piece.team == Team.BLACK && position.row == 0) ||
                (piece.team == Team.WHITE && position.row == 7))
            )
                pieces[position] = Queen(piece.team)
        }
    }
}

data class Position(val row : Int, val col : Int){
    override fun equals(other: Any?) = (other is Position && row == other.row && col == other.col)
    override fun hashCode() =  42 * row + 7 * col
}
data class Move(val origin : Position, val destination : Position)