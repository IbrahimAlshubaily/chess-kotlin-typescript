package com.pxf.chess

enum class Team {
    BLACK, WHITE;

    fun getOpponent(): Team {
        if (this == BLACK)
            return WHITE
        return BLACK
    }
}