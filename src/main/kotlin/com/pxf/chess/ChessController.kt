package com.pxf.chess

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.StringJoiner

@RestController
class ChessController() {

    private val chess: Chess = Chess()

    @GetMapping("/board")
    fun getChessBoard():String {
        println(chess)
        return chess.toString()
    }
}