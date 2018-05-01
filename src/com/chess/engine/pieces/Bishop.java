package com.chess.engine.pieces;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.chess.engine.*;
import com.chess.engine.board.*;

public class Bishop extends Piece{

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDENATES = {-9, -7, 7, 9};
	
	
	
	public Bishop(Alliance pieceAlliance,  int piecePostion) {
		super(PieceType.BISHOP, pieceAlliance, piecePostion, true);

	}

	public Bishop(Alliance pieceAlliance,  int piecePostion, boolean isFirstMove) {
		super(PieceType.BISHOP, pieceAlliance, piecePostion, isFirstMove);

	}
	@Override
	public List<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		for (final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDENATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				if(isFirstColumnExclusion(candidateDestinationCoordinate, 
					candidateCoordinateOffset) || 
					isEighthColumnExclusion(candidateDestinationCoordinate, 
								candidateCoordinateOffset)) {
					break;
				}			
				candidateDestinationCoordinate += candidateCoordinateOffset;
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
						
						break;
					}
				}
			}
		}
		
		
		return Collections.unmodifiableList(legalMoves);
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

	private static boolean isFirstColumnExclusion(final int currentPostion,
												  final int canadidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPostion] && (canadidateOffset == -9 || canadidateOffset == 7);
	}
	
	private static boolean isEighthColumnExclusion(final int currentPostion, 
												   final int canadidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPostion] && (canadidateOffset == -7 || canadidateOffset == 9 );
	}

	

}
