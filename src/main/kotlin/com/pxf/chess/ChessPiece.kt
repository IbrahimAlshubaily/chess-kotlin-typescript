package com.pxf.chess

class ChessPiece(private val repr: String, private val team : String) {
    override fun toString(): String = team.plus(repr)
}
