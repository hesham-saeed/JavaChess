package com.chess.engine.pieces.AI;

import com.chess.engine.board.*;

public interface BoardEvaluator {

   abstract int evaluate(Board board, int depth);
}
