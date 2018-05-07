package com.chess.engine.pieces;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.AI.MoveStrategy;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyFactory;

public class Bishop extends Piece{


	public Bishop(Alliance pieceAlliance,  int piecePostion) {
		super(PieceType.BISHOP, pieceAlliance, piecePostion, true, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.BISHOP));

	}

	public Bishop(Alliance pieceAlliance,  int piecePostion, boolean isFirstMove) {
		super(PieceType.BISHOP, pieceAlliance, piecePostion, isFirstMove, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.BISHOP));

	}
	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		return moveStrategy.calculateLegalMoves(board, this);
	}
	
	@Override
	public String toString() {
		return PieceType.BISHOP.toString();
	}
	
	@Override
	public Alliance getPieceAlliance() {
		 return this.pieceAlliance;
	}

	@Override
	public Bishop movePiece(Move move) {
		return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}


}
