package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.KingMoveStrategyPiece;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyContext;

public class King extends Piece{

	public King(final Alliance pieceAlliance,final int piecePostion) {

		super(PieceType.KING, pieceAlliance, piecePostion, true, new KingMoveStrategyPiece());
	}

	public King(final Alliance pieceAlliance,final int piecePostion, boolean isFirstMove) {

		super(PieceType.KING, pieceAlliance, piecePostion, isFirstMove, new KingMoveStrategyPiece());
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {

		return moveStrategy.excuteCalculateLegalMoves(board, this);

	}

	@Override
	public King movePiece(Move move) {
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public String toString() {
		return PieceType.KING.toString();
	}

	@Override
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}

}
