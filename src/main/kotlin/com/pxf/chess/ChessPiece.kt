package com.pxf.chess

open class ChessPiece(
    val repr: String,
    val team: Team,
    val huresticValue: Int,
    private val nSteps: Int,
    private val directions: List<Direction>
) {

    open fun getMoves(pieces : HashMap<Position, ChessPiece>) : List<Position> {

        val out = mutableListOf<Position>()
        val myPosition = pieces.filter { it.value == this }.keys.first()

        for (dir in directions) {
            var currPosition = myPosition
            for (i in 1..nSteps) {

                currPosition = dir.step(currPosition, team)
                if(currPosition.row < 0 || currPosition.col < 0 ||
                        currPosition.row > 7 || currPosition.col > 7)
                    break

                if (pieces[currPosition]?.team != team)
                    out.add(currPosition)

                if (pieces.contains(currPosition))
                    break
            }
        }
        return out
    }
    fun huresticValue() = huresticValue
    override fun toString(): String = " ".plus(team.toString().first()).plus(repr).plus("  ")
}

class Pawn(team : Team) : ChessPiece("P", team, 1, 2, listOf(Direction.FORWARD)) {
    override fun getMoves(pieces: HashMap<Position, ChessPiece>): List<Position> {

        val moves = super.getMoves(pieces)
            .filter { !pieces.contains(it) }
            .toMutableList()

        val myPosition = pieces.filter { it.value == this }.keys.first()
        for (dir in listOf(Direction.FORWARD_LEFT, Direction.FORWARD_RIGHT)){
            val currPosition = dir.step(myPosition, team)
            if (pieces[currPosition]?.team == team.getOpponent())
                moves.add(currPosition)
        }
        return moves
    }
}

class Rook(team : Team) : ChessPiece("R", team, 6, 8, Direction.getStraightDirections())
class Bishop(team : Team) : ChessPiece("B", team,6,  8, Direction.getDiagonalDirections())
class Knight(team : Team) : ChessPiece("N", team, 4, 1, Direction.getKnightDirections())
class Queen(team : Team) : ChessPiece("Q", team, 20, 8, Direction.getAllDirections())
class King(team : Team) : ChessPiece("K", team, Int.MAX_VALUE, 1, Direction.getAllDirections())
