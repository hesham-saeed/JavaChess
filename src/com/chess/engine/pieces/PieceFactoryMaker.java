package com.chess.engine.pieces;

import com.chess.engine.Alliance;

public class PieceFactoryMaker {

    private static PieceFactoryMaker instance = new PieceFactoryMaker();

    private PieceFactoryMaker(){

    }

    public Piece choosePieceType (final Piece.PieceType pieceType,
                                  final Alliance pieceAlliance,
                                  final int piecePosition,
                                  final boolean isFirstMove) {

        if(pieceType == Piece.PieceType.BISHOP) {

            return new Bishop(pieceAlliance,
                                piecePosition,
                                isFirstMove);

        } else if(pieceType == Piece.PieceType.KING) {

            return new King(pieceAlliance,
                             piecePosition,
                             isFirstMove);

        } else if(pieceType == Piece.PieceType.KNIGHT) {

            return new Knight(pieceAlliance,
                              piecePosition,
                              isFirstMove);


        } else if(pieceType == Piece.PieceType.PAWN) {

            return new Pawn(pieceAlliance,
                            piecePosition,
                            isFirstMove);


        } else if(pieceType == Piece.PieceType.QUEEN) {

            return new Queen(pieceAlliance,
                            piecePosition,
                            isFirstMove);


        } else {

            return new Rook(pieceAlliance,
                    piecePosition,
                    isFirstMove);


        }

    }





    public static PieceFactoryMaker getInstance(){

        return instance;
    }

}
