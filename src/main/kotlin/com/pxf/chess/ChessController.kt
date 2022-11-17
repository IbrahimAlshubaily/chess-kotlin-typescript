package com.pxf.chess

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ChessController(private val chess: Chess = Chess()) {

    @GetMapping("/board")
    fun getChessBoard() = chess.toString()

    @PostMapping("/move")
    fun moveChessPiece(@RequestBody move : Move) = chess.movePiece(move)
}
