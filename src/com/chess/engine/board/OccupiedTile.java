package com.chess.engine.board;

import com.chess.engine.pieces.*;

public final class OccupiedTile extends Tile{
        Piece pieceOnTile;
        OccupiedTile(int tileCoordinate, Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }
        @Override
        public String toString() {
            return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }
        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }