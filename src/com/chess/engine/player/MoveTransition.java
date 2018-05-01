package com.chess.engine.player;

import com.chess.engine.board.*;

import java.awt.geom.PathIterator;

public class MoveTransition {

    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition (final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
        this.move = move;
        this.moveStatus = moveStatus;
        this.transitionBoard = transitionBoard;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
