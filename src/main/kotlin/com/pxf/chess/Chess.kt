package com.pxf.chess


data class Position(val row : Int, val col : Int){
    override fun equals(other: Any?) = (other is Position && row == other.row && col == other.col)
    override fun hashCode() =  42 * row + 7 * col
}
data class Move(val origin : Position, val destination : Position)

class Chess {
    private val pieces: MutableMap<Position, ChessPiece> = initPieces()
    private fun initPieces(): MutableMap<Position, ChessPiece> {
        val pieces = initTeam("W", 0, 1)
        pieces.putAll(initTeam("B", 7, 6))
        return pieces.toSortedMap(compareBy<Position> { it.row }.thenBy { it.col })
    }

    private fun initTeam(team: String, mainRow: Int, pawnRow: Int): MutableMap<Position, ChessPiece> {

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
        pieces.forEach { entry ->
            out[entry.key.row][entry.key.col] = entry.value.toString()
        }
        return buildString{
            for (row in out)
                append(row.contentToString().plus("\n"))
        }
    }


    fun getPiecesCount(): Int {
        return pieces.size
    }

    fun getPiece(predicate: (ChessPiece) -> (Boolean)): Pair<Position, ChessPiece> {
        val out = pieces.filter { predicate(it.value) }.entries.first()
        return Pair(out.key, out.value)
    }

    fun getPieces(): HashMap<Position, ChessPiece> {
        return HashMap(pieces)
    }

    fun movePiece(move: Move): Boolean {
        val piece = pieces[move.origin]
        if (piece != null) {
            if (piece.getMoves(HashMap(pieces)).contains(move.destination)) {
                pieces.remove(move.origin)
                pieces[move.destination] = piece
                return true
            }
        }
        return false
    }

    fun getMoves(position: Position): List<Position>?{
        val piece = pieces.get(position)
        return piece?.getMoves(HashMap(pieces))
    }
}

fun main(){
    val chess = Chess()
    println(chess)
}

