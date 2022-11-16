package com.pxf.chess

data class Position(val row : Int, val col : Int)

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

    fun print2D(gridSize: Int = 8) {
        val out = Array(gridSize) { Array<String>(gridSize) { " --  " } }
        pieces.forEach { entry ->
            out[entry.key.row][entry.key.col] = entry.value.toString()
        }
        for (row in out)
            println(row.contentToString())
    }
}

fun main(){
    val chess = Chess()
    chess.print2D()
}

