package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.*;
import com.chess.engine.pieces.*;

public class Builder{
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;

        public Builder(){
            this.boardConfig = new HashMap<>(33, 1.0f);
        }
        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return  new Board(this);
        }

    public void setEnPassantPawn(Pawn enPassantPawn) {
        this.enPassantPawn = enPassantPawn;
    }

    public Pawn getEnPassantPawn() {
        return enPassantPawn;
    }
}