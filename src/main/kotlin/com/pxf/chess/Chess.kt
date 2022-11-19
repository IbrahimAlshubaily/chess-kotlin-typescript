package com.pxf.chess


class Chess {
    private var pieces: HashMap<Position, ChessPiece> = initPieces()
    private fun initPieces(): HashMap<Position, ChessPiece> {
        return HashMap(
            initTeam(Team.WHITE, 0, 1)
            .plus(initTeam(Team.BLACK, 7, 6))
            .toSortedMap(compareBy<Position> { it.row }.thenBy { it.col }))
    }

    private fun initTeam(team: Team, mainRow: Int, pawnRow: Int): MutableMap<Position, ChessPiece> {

        val out = mutableMapOf(
            Position(mainRow, 0) to Rook(team),
            Position(mainRow, 1) to Bishop(team),
            Position(mainRow, 2) to Knight(team),

            Position(mainRow, 3) to Queen(team),
            Position(mainRow, 4) to King(team),

            Position(mainRow, 5) to Knight(team),
            Position(mainRow, 6) to Bishop(team),
            Position(mainRow, 7) to Rook(team)
        )

        for (i in 0..7)
            out[Position(pawnRow, i)] = Pawn(team)

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

    fun minMaxStep(){
        pieces = MinMax.getBestMoves(pieces)
    }

    fun getMoves(position: Position) =
        if (pieces[position]?.team == Team.BLACK) pieces[position]?.getMoves(pieces)
        else emptyList()

    fun getPieces() = pieces

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

fun main(){
    val chess = Chess()
    chess.minMaxStep()
    println(chess)
}


data class Position(val row : Int, val col : Int){
    override fun equals(other: Any?) = (other is Position && row == other.row && col == other.col)
    override fun hashCode() =  42 * row + 7 * col
}
data class Move(val origin : Position, val destination : Position)