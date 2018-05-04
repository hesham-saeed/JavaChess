package com.chess.engine.board;

import com.chess.engine.pieces.*;

public class CastleMove extends Move {

    protected final Rook castleRook;
    protected final int castleRookStart ;
    protected final int castleRookDestination;


    protected CastleMove(Board board, Piece movedPiece,
                         int destinationCoordinate,
                         final Rook castleRook,
                         final int castleRookStart,
                         final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookDestination = castleRookDestination;
    }

    public Rook getCastleRook() {
        return this.castleRook;
    }

    @Override
    public boolean isCastlingMove(){
        return true;
    }

    @Override
    public Board execute() {
        final Builder builder = new Builder();
        for(final Piece piece: this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookDestination));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.castleRook.hashCode();
        result = prime * result + this.castleRookDestination;
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CastleMove)) {
            return false;
        }
        final CastleMove otherCastleMove = (CastleMove) other;
        return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
    }
}
