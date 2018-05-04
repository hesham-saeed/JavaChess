package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;


public class Pawn extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};
	
	
	public Pawn(final Alliance pieceAlliance, final int piecePostion) {

		super(PieceType.PAWN, pieceAlliance, piecePostion, true);
	}

	public Pawn(final Alliance pieceAlliance, final int piecePostion,boolean isFirstMove) {

		super(PieceType.PAWN, pieceAlliance, piecePostion, isFirstMove);
	}

	@Override
	public List<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for (final int currentCoordinateOffest : CANDIDATE_MOVE_COORDINATE) {
			
			final int candidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * currentCoordinateOffest);

			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				continue;
			}
			
			if (currentCoordinateOffest == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied())
			{
				legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
			}
			
			else if (currentCoordinateOffest == 16 &&
					 this.isFirstMove() &&
					 ((BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) ||
							(BoardUtils.SEVENTH_ROW[this.piecePosition]  && this.getPieceAlliance().isWhite())))
			{
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
				if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
						!board.getTile(candidateDestinationCoordinate).isTileOccupied())
				{
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}
			}
			
			else if (currentCoordinateOffest == 7 && 
					  !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] &&  this.pieceAlliance.isWhite()) || 
							  (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))
			{
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
			
			else if (currentCoordinateOffest == 9 && 
					  !((BoardUtils.FIRST_COLUMN[this.piecePosition] &&  this.pieceAlliance.isWhite()) || 
							  (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))
			{
				if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
			}
		}
		 
		return Collections.unmodifiableList(legalMoves);
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

}
