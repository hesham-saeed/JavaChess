package com.chess.engine.board;

import com.chess.engine.pieces.*;

public class CastleMove extends Move {

    protected final Rook castleRook;
    protected final int castleRookStar;
    protected final int castleRookDestination;


    protected CastleMove(Board board, Piece movedPiece,
                         int destinationCoordinate,
                         final Rook castleRook,
                         final int castleRookStar,
                         final int castleRookDestination) {
        super(board, movedPiece, destinationCoordinate);
        this.castleRook = castleRook;
        this.castleRookStar = castleRookStar;
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
}
