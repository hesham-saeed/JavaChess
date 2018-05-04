package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;

public class Queen extends Piece{

	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDENATES = {-9, -8, -7, -1, 1, 7, 8, 9};

	
	public Queen(final Alliance pieceAlliance, final int piecePostion) {
		super(PieceType.QUEEN, pieceAlliance, piecePostion, true);
		
	}

	public Queen(final Alliance pieceAlliance, final int piecePostion, final boolean isFirstMove) {
		super(PieceType.QUEEN, pieceAlliance, piecePostion, isFirstMove);

	}

	@Override
	public List<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDENATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) || isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				candidateDestinationCoordinate += candidateCoordinateOffset;
				if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
					break;
				}
				else{
					final Tile candidtaeDestinatioTile = board.getTile(candidateDestinationCoordinate);
					if(!candidtaeDestinatioTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
					else {
						final Piece pieceAtDestination = candidtaeDestinatioTile.getPiece();
						final Alliance pieceAlliance =  pieceAtDestination.getPieceAlliance();
						if(this.pieceAlliance != pieceAlliance)
						{
							legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)) ;
						}

						break;
					}
				}
			}
		}


		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.QUEEN.toString();
	}
	
	private static boolean isFirstColumnExclusion(final int currentPostion, final int canadidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPostion] && (canadidateOffset == -9 || canadidateOffset == -1 || canadidateOffset == 7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPostion, final int canadidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPostion] && (canadidateOffset == -7 || canadidateOffset == 1 || canadidateOffset == 9 );
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
