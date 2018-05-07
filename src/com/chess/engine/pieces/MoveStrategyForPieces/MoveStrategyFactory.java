package com.chess.engine.pieces.MoveStrategyForPieces;

import com.chess.engine.pieces.Piece;


public class MoveStrategyFactory {

    private static MoveStrategyFactory instance = new MoveStrategyFactory();

    private MoveStrategyFactory() {

    }

    public MoveStrategyPiece chooseMoveType (final Piece.PieceType pieceType) {

        if(pieceType == Piece.PieceType.BISHOP) {

            return new BishopMoveStrategyPiece();

        } else if(pieceType == Piece.PieceType.KING) {

            return new KingMoveStrategyPiece();

        } else if(pieceType == Piece.PieceType.KNIGHT) {

            return new KnightMoveStrategyPiece();

        } else if(pieceType == Piece.PieceType.PAWN) {

            return new PawnMoveStrategyPiece();

        } else if(pieceType == Piece.PieceType.QUEEN) {

            return new QueenMoveStrategyPiece();

        } else {

            return new RookMoveStrategyPiece();

        }

    }





    public static MoveStrategyFactory getInstance(){

        return instance;
    }
}
