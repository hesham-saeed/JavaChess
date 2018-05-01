package com.chess.engine.board;

import com.chess.engine.pieces.*;

public final class MajorMove extends Move{

        public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }

}