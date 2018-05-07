package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.MoveStrategyForPieces.MoveStrategyContext;
import com.chess.engine.pieces.MoveStrategyForPieces.PawnMoveStrategyPiece;


public class Pawn extends Piece{

	public Pawn(final Alliance pieceAlliance, final int piecePostion) {

		super(PieceType.PAWN, pieceAlliance, piecePostion, true, new PawnMoveStrategyPiece());
	}

	public Pawn(final Alliance pieceAlliance, final int piecePostion,boolean isFirstMove) {

		super(PieceType.PAWN, pieceAlliance, piecePostion, isFirstMove, new PawnMoveStrategyPiece());
	}

	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		return moveStrategy.excuteCalculateLegalMoves(board, this);

	}

	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}


	@Override
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}

	public Piece getPromotionPiece() {
		return PieceFactoryMaker.getInstance().choosePieceType(PieceType.QUEEN, this.pieceAlliance, this.piecePosition,  false);
	}

}
