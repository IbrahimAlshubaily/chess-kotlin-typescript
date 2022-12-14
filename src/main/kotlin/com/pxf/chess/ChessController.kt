package com.pxf.chess

import org.json.JSONArray
import org.json.JSONObject
import org.springframework.web.bind.annotation.*

@RestController
class ChessController(private val chess: Chess = Chess()) {

    @GetMapping("/board")
    @CrossOrigin
    fun getChessBoard() = JSONArray(chess.getPieces().map {
        JSONObject(mapOf(
                "row" to it.key.row ,
                "col" to it.key.col ,
                "team" to it.value.team.toString().first().uppercase() ,
                "repr" to it.value.repr
        ))
    }).toString()

    @GetMapping("/boardstr")
    @CrossOrigin
    fun getChessBoardStr() = chess.toString()

    @GetMapping("/moves/{row}/{col}")
    @CrossOrigin
    fun getPieceMoves(@PathVariable(value="row") row: String, @PathVariable(value="col") col: String):String {
        val position = Position(Integer.parseInt(row), Integer.parseInt(col))
        val moves = chess.getMoves(position)
        return JSONArray(moves?.map {
            JSONObject(
                mapOf(
                    "row" to it.row,
                    "col" to it.col,
                )
            )
        }).toString()
    }

    @PostMapping("/move")
    @CrossOrigin
    fun moveChessPiece(@RequestBody move : Move) : String {
        val out = JSONObject()
        if (chess.getPieces()[move.origin]?.team == Team.BLACK){
            val moved = chess.movePiece(move)
            if (moved){
                out.put("move", chess.minMaxStep())
                out.put("board", getChessBoard())
            }
        }
        return out.toString()
    }

}