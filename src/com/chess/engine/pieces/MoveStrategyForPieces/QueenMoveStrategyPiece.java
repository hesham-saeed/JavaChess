package com.chess.engine.pieces.MoveStrategyForPieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueenMoveStrategyPiece extends MoveStrategyPiece {
    public QueenMoveStrategyPiece() {

        super(new int[] {-9, -8, -7, -1, 1, 7, 8, 9});
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board, Piece piece) {

        final List<Move> legalMoves = new ArrayList<>();

        for (final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDENATES) {
            int candidateDestinationCoordinate = piece.getPiecePosition();
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
                        legalMoves.add(new MajorMove(board, piece, candidateDestinationCoordinate));
                    }
                    else {
                        final Piece pieceAtDestination = candidtaeDestinatioTile.getPiece();
                        final Alliance pieceAlliance =  pieceAtDestination.getPieceAlliance();
                        if(piece.getPieceAlliance() != pieceAlliance)
                        {
                            legalMoves.add(new MajorAttackMove(board, piece, candidateDestinationCoordinate, pieceAtDestination)) ;
                        }

                        break;
                    }
                }
            }
        }


        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPostion, final int canadidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPostion] && (canadidateOffset == -9 || canadidateOffset == -1 || canadidateOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPostion, final int canadidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPostion] && (canadidateOffset == -7 || canadidateOffset == 1 || canadidateOffset == 9 );
    }
}
