 package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyFactory;


 public class Rook extends Piece{

	public Rook(final Alliance pieceAlliance, final int piecePostion) {
		super(PieceType.ROOK, pieceAlliance, piecePostion,true, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.ROOK));
	}

	 public Rook(final Alliance pieceAlliance, final int piecePostion, final boolean isFirstMove) {
		 super(PieceType.ROOK, pieceAlliance, piecePostion, isFirstMove, MoveStrategyFactory.getInstance().chooseMoveType(PieceType.ROOK));
	 }

	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		return moveStrategy.calculateLegalMoves(board, this);

	}
	
	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}

	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}


}
