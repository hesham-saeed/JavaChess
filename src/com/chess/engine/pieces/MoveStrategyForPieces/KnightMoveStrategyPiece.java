package com.chess.engine.pieces.MoveStrategyForPieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.*;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KnightMoveStrategyPiece extends MoveStrategyPiece {
    public KnightMoveStrategyPiece() {

        super(new int[] {-17,-15,-10,-6,6,10,15,17});
    }

    @Override
    public List<Move> calculateLegalMoves(final Board board, Piece piece) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidateOffset : this.CANDIDATE_MOVE_VECTOR_COORDENATES) {
            if(isFirstColumnExclusion(piece.getPiecePosition(), currentCandidateOffset) ||
                    isSecondColumnExclusion(piece.getPiecePosition(), currentCandidateOffset) ||
                    isSeventhColumnExclusion(piece.getPiecePosition(), currentCandidateOffset) ||
                    isEighthColumnExclusion(piece.getPiecePosition(), currentCandidateOffset)) {
                continue;
            }
            final int candidateDestinationCoordinate = piece.getPiecePosition() + currentCandidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, piece, candidateDestinationCoordinate));
                } else {
                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAlliance();
                    if (piece.getPieceAlliance() != pieceAtDestinationAllegiance) {
                        legalMoves.add(new MajorAttackMove(board, piece, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition,
                                                  final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -17) ||
                (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPosition,
                                                   final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition,
                                                    final int candidateOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition,
                                                   final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }
}
