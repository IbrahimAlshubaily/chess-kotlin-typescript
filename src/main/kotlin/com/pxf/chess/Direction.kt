package com.pxf.chess

enum class Direction(private val rowOffset: Int, private val colOffset: Int) {
    LEFT(0, 1),
    RIGHT(0, -1),
    FORWARD(1, 0),
    BACKWARD(-1, 0),
    FORWARD_LEFT(1, 1),
    FORWARD_RIGHT(1, -1),
    BACKWARD_LEFT(-1, 1),
    BACKWARD_RIGHT(-1, -1),

    KNIGHT_A(2, 1),
    KNIGHT_B(2, -1),
    KNIGHT_C(-2, 1),
    KNIGHT_D(-2, -1),
    KNIGHT_E(1, 2),
    KNIGHT_F(1, -2),
    KNIGHT_G(-1, 2),
    KNIGHT_H(-1, -2);

    fun step(currPosition: Position, team: String) : Position {
        val projection = if(team.equals("w")) 1 else -1
        return Position(
            currPosition.row + (projection *rowOffset),
            currPosition.col + (projection *colOffset)
        )
    }

    companion object {
        fun getKnightDirections() = listOf(
            KNIGHT_A, KNIGHT_B, KNIGHT_C, KNIGHT_D,
            KNIGHT_E, KNIGHT_F, KNIGHT_G, KNIGHT_H)

        fun getStraightDirections() = listOf( FORWARD, BACKWARD, LEFT, RIGHT)
        fun getDiagonalDirections() = listOf( FORWARD_LEFT, FORWARD_RIGHT, BACKWARD_LEFT, BACKWARD_RIGHT)
        fun getAllDirections() = listOf(getStraightDirections(), getDiagonalDirections()).flatten()
    }
}