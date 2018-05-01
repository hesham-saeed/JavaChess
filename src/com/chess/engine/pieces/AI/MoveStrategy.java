package com.chess.engine.pieces.AI;

import com.chess.engine.board.*;

public interface MoveStrategy {

    abstract Move execute(Board board);

}
