package com.pxf.chess

class MinMax {
    //make me faster! (instantanious)
    companion object{
        fun getBestMoves(chess : Chess) : Chess {

            return chess.getMoves(Team.WHITE)
                .parallelStream()
                .map { Pair(min(it, 3), it) }
                .max(compareBy { it.first })
                .get().second
        }

        private fun min(chess :  Chess, depth : Int) : Int {

            if (depth == 0)
                return eval(chess.getPieces())
            return chess.getMoves(Team.BLACK).minOf { max(it, depth-1) }
        }

        private fun max(chess: Chess, depth: Int): Int {
            if (depth == 0)
                return eval(chess.getPieces())
            return chess.getMoves(Team.WHITE).maxOf { min(it, depth-1) }
        }

        private fun eval(pieces: HashMap<Position, ChessPiece>): Int{
            return evalTeam(pieces, Team.WHITE) - evalTeam(pieces, Team.BLACK)
        }
        private fun evalTeam(pieces: HashMap<Position, ChessPiece>, team: Team): Int {
            return pieces.filter { it.value.team == team }.map { it.value.huresticValue() }.sum()
        }
    }

}