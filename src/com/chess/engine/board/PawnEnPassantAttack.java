package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

  class PawnEnPassantAttack extends PawnAttackMove {
    public PawnEnPassantAttack(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }
}
