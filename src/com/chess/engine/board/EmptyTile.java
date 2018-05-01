package com.chess.engine.board;

import com.chess.engine.pieces.*;

public final class EmptyTile extends Tile{
        public EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
        @Override
        public String toString() {
            return "-";
        }
    }