package com.pxf.chess

open class ChessPiece(
    private val repr: String,
    val team: String,
    private val nSteps: Int,
    private val directions: List<Direction>
) {

    fun getMoves(pieces : HashMap<Position, ChessPiece>) : List<Position> {

        val out = mutableListOf<Position>()
        val myPosition = pieces.filter { it.value == this }.keys.first()

        for (dir in directions) {
            var currPosition = myPosition
            for (i in 1..nSteps) {

                currPosition = dir.step(currPosition, team)
                if (pieces[currPosition]?.team != team)
                    out.add(currPosition)

                if (pieces.contains(currPosition))
                    break
            }
        }
        return out
    }
    override fun toString(): String = " ".plus(team).plus(repr).plus("  ")
}

class Rook(team : String) :
    ChessPiece("R", team, 8, Direction.getStraightDirections())

class Bishop(team : String) :
    ChessPiece("B", team, 8, Direction.getDiagonalDirections())

class Knight(team : String) :
    ChessPiece("N", team, 1, Direction.getKnightDirections())

class Queen(team : String) :
    ChessPiece("Q", team, 8, Direction.getAllDirections())

class King(team : String) :
    ChessPiece("K", team, 1, Direction.getAllDirections())

class Pawn(team : String) :
    ChessPiece("P", team, 2, listOf(Direction.FORWARD)){}





