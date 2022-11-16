package com.pxf.chess

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ChessPieceTests {

    @BeforeEach
    fun setUp(){
    }

    @Test
    fun initTest(){
        val chess = Chess()
        assertEquals(32, chess.getPiecesCount())
    }
    @Test
    fun pawnMoves(){
        val chess = Chess()
        val (pos, pawn) = chess.getPiece { it is Pawn }
        assertEquals(
            listOf(
                Direction.FORWARD.step(pos, pawn.team),
                Direction.FORWARD.step(Direction.FORWARD.step(pos, pawn.team), pawn.team)
            )
            , pawn.getMoves(chess.getPieces())
        )
    }
}