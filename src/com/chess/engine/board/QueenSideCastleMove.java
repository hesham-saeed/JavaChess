package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public class QueenSideCastleMove extends CastleMove {
    public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook,
                               final int castleRookStar,
                               final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate, castleRook, castleRookStar, castleRookDestination);
    }
    @Override
    public String toString(){
        return "O-O-O";
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof QueenSideCastleMove && super.equals(other);
    }
}
