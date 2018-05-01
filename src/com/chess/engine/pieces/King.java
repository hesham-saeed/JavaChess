package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;

public class King extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
	
	public King(final Alliance pieceAlliance,final int piecePostion) {

		super(PieceType.KING, pieceAlliance, piecePostion, true);
	}

	public King(final Alliance pieceAlliance,final int piecePostion, boolean isFirstMove) {

		super(PieceType.KING, pieceAlliance, piecePostion, isFirstMove);
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new  ArrayList<>();
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
				continue;
			}
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				final Tile candidtaeDestinatioTile = board.getTile(candidateDestinationCoordinate);
				if(!candidtaeDestinatioTile.isTileOccupied()) {	
					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				} 
				else {
					final Piece pieceAtDestination = candidtaeDestinatioTile.getPiece();
					final Alliance pieceAlliance =  pieceAtDestination.getPieceAlliance();
					if(this.pieceAlliance != pieceAlliance)
					{
						legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)) ;
					}
				}
			}	
		}
		
		return Collections.unmodifiableList(legalMoves);
	}

	@Override
	public King movePiece(Move move) {
		return new King(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

	@Override
	public String toString() {
		return PieceType.KING.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPostion, final int canadidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPostion] && (canadidateOffset == -9 || canadidateOffset == -1 || canadidateOffset == 7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPostion, final int canadidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPostion] && (canadidateOffset == -7 || canadidateOffset == 1 || canadidateOffset == 9);
	}

	@Override
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}

}
