package com.pxf.chess

class MinMax {

    companion object{
        fun getBestMoves(pieces: HashMap<Position, ChessPiece> ) : HashMap<Position, ChessPiece> {

             return pieces.filter { it.value.team == Team.WHITE }
                .map {
                    applyMove(pieces, it.key, it.value.getMoves(pieces))
                }
                 .flatten()
                 .parallelStream()
                 .map { Pair(min(it, 3), it) }
                .max(compareBy { it.first })
                .get().second
        }



        private fun min(pieces: HashMap<Position, ChessPiece>, depth : Int) : Int {

            if (depth == 0)
                return eval(pieces)


            return pieces.filter { it.value.team == Team.BLACK }
                .map {
                    applyMove(pieces, it.key, it.value.getMoves(pieces))
                }
                .flatten().map {
                    max(it, depth-1)
                }.minOf { it }
        }

        private fun max(pieces: HashMap<Position, ChessPiece>, depth: Int): Int {
            if (depth == 0)
                return eval(pieces)
            return pieces.filter { it.value.team == Team.WHITE }
                .map {
                    applyMove(pieces, it.key, it.value.getMoves(pieces))
                }
                .flatten().map {
                    min(it, depth-1)
                }.maxOf { it }
        }

        private fun applyMove(pieces: HashMap<Position, ChessPiece>, origin: Position, destinations: List<Position>): List<HashMap<Position, ChessPiece>> {
            return destinations.map {
                Chess.makeMove(pieces, Move(origin, it))
            }
        }


        private fun eval(pieces: HashMap<Position, ChessPiece>): Int{
            return evalTeam(pieces, Team.WHITE) - evalTeam(pieces, Team.BLACK)
        }
        private fun evalTeam(pieces: HashMap<Position, ChessPiece>, team: Team): Int {
            return pieces.filter { it.value.team == team }.map { it.value.huresticValue() }.sum()
        }
    }

}