package com.pxf.chess

open class ChessPiece(
    private val repr: String,
    private val team: String,
    val nSteps: Int,
    private val direction: List<Direction>
) {

    override fun toString(): String = " ".plus(team).plus(repr).plus("  ")

}

class Rook(private val team : String) :
    ChessPiece("R", team, 8, Direction.getStraightDirections())

class Bishop(private val team : String) :
    ChessPiece("B", team, 8, Direction.getDiagonalDirections())

class Knight(private val team : String) :
    ChessPiece("N", team, 1, Direction.getKnightDirections())

class Queen(private val team : String) :
    ChessPiece("Q", team, 8, Direction.getAllDirections())

class King(private val team : String) :
    ChessPiece("K", team, 1, Direction.getAllDirections())

class Pawn(private val team : String) :
    ChessPiece("P", team, 2, listOf(Direction.FORWARD)){}





