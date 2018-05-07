package com.chess.engine.pieces;



import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.BishopMoveStrategyPiece;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyContext;

public class Bishop extends Piece{


	public Bishop(Alliance pieceAlliance,  int piecePostion) {
		super(PieceType.BISHOP, pieceAlliance, piecePostion, true, new BishopMoveStrategyPiece());

	}

	public Bishop(Alliance pieceAlliance,  int piecePostion, boolean isFirstMove) {
		super(PieceType.BISHOP, pieceAlliance, piecePostion, isFirstMove, new BishopMoveStrategyPiece());

	}
	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		return moveStrategy.excuteCalculateLegalMoves(board, this);
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
