package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyFactory;


public class Knight extends Piece {


    public Knight(final Alliance pieceAlliance, final int piecePosition) {

        super(PieceType.KNIGHT, pieceAlliance, piecePosition, true, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.KNIGHT));
    }
    public Knight(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove) {

        super(PieceType.KNIGHT, pieceAlliance, piecePosition, isFirstMove, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.KNIGHT));
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board) {

        return moveStrategy.calculateLegalMoves(board, this);

    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
	public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

}
