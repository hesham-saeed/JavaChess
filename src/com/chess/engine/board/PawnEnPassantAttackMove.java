package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

  public class PawnEnPassantAttackMove extends PawnAttackMove {
    public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
        super(board, movedPiece, destinationCoordinate, attackedPiece);
    }

      @Override
      public boolean equals(final Object other) {
          return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
      }

      @Override
      public Board execute() {
          final Builder builder = new Builder();
          for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
              if (!this.movedPiece.equals(piece)) {
                  builder.setPiece(piece);
              }
          }
          for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
              if(!piece.equals(this.getAttackedPiece())) {
                  builder.setPiece(piece);
              }
          }
          builder.setPiece(this.movedPiece.movePiece(this));
          builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
          return builder.build();
      }
}
