package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyFactory;

public class Queen extends Piece{

	public Queen(final Alliance pieceAlliance, final int piecePostion) {
		super(PieceType.QUEEN, pieceAlliance, piecePostion, true, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.QUEEN));
		
	}

	public Queen(final Alliance pieceAlliance, final int piecePostion, final boolean isFirstMove) {
		super(PieceType.QUEEN, pieceAlliance, piecePostion, isFirstMove, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.QUEEN));

	}

	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		return moveStrategy.calculateLegalMoves(board, this);

	}
	
	@Override
	public String toString() {
		return PieceType.QUEEN.toString();
	}

	@Override
	public Queen movePiece(Move move) {
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}


}
