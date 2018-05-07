 package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyContext;
import com.chess.engine.pieces.MoveStrategyForPieces.RookMoveStrategyPiece;


 public class Rook extends Piece{

	public Rook(final Alliance pieceAlliance, final int piecePostion) {
		super(PieceType.ROOK, pieceAlliance, piecePostion,true, new RookMoveStrategyPiece());
	}

	 public Rook(final Alliance pieceAlliance, final int piecePostion, final boolean isFirstMove) {
		 super(PieceType.ROOK, pieceAlliance, piecePostion, isFirstMove, new RookMoveStrategyPiece());
	 }

	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		return moveStrategy.excuteCalculateLegalMoves(board, this);

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
