package com.chess.engine.player;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public WhitePlayer getOpponent() {
        return (WhitePlayer) this.board.whitePlayer();
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isInCheckMate()) {

            if(!this.board.getTile(5).isTileOccupied() && !this.board.getTile(6).isTileOccupied()) {
                final Tile rookTile = this.board.getTile(7);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if(Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() && Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 6, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }

            if(!this.board.getTile(1).isTileOccupied()
                    && !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()) {

                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() &&
                    Player.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                    Player.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                    rookTile.getPiece().getPieceType().isRook()) {
                    kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 2, (Rook)rookTile.getPiece(), rookTile.getTileCoordinate(), 1));
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }


}
