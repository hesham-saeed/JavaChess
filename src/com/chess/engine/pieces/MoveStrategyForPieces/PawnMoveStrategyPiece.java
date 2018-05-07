package com.chess.engine.pieces.MoveStrategyForPieces;

import com.chess.engine.board.*;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PawnMoveStrategyPiece extends MoveStrategyPiece {
    public PawnMoveStrategyPiece() {

        super(new int[] {7, 8, 9, 16});

    }

    @Override
    public List<Move> calculateLegalMoves(final Board board, Piece piece) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCoordinateOffest : this.CANDIDATE_MOVE_VECTOR_COORDENATES) {

            final int candidateDestinationCoordinate = piece.getPiecePosition() + (piece.getPieceAlliance().getDirection() * currentCoordinateOffest);

            if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
            {
                continue;
            }

            if (currentCoordinateOffest == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied())
            {
                if (piece.getPieceAlliance().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(new PawnMove(board, piece, candidateDestinationCoordinate)));
                }
                else {
                    legalMoves.add(new PawnMove(board, piece, candidateDestinationCoordinate));
                }
            }

            else if (currentCoordinateOffest == 16 &&
                    piece.isFirstMove() &&
                    ((BoardUtils.SECOND_ROW[piece.getPiecePosition()] && piece.getPieceAlliance().isBlack()) ||
                            (BoardUtils.SEVENTH_ROW[piece.getPiecePosition()]  && piece.getPieceAlliance().isWhite())))
            {
                final int behindCandidateDestinationCoordinate = piece.getPiecePosition() + (piece.getPieceAlliance().getDirection() * 8);
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied())
                {
                    legalMoves.add(new PawnJump(board, piece, candidateDestinationCoordinate));
                }
            }

            else if (currentCoordinateOffest == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[piece.getPiecePosition()] &&  piece.getPieceAlliance().isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[piece.getPiecePosition()] && piece.getPieceAlliance().isBlack())))
            {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(piece.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        if (piece.getPieceAlliance().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, piece, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else{
                            legalMoves.add(new PawnAttackMove(board, piece, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (piece.getPiecePosition() + (piece.getPieceAlliance().getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (piece.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(
                                new PawnEnPassantAttackMove(board, piece, candidateDestinationCoordinate, pieceOnCandidate));

                    }
                }
            }

            else if (currentCoordinateOffest == 9 &&
                    !((BoardUtils.FIRST_COLUMN[piece.getPiecePosition()] &&  piece.getPieceAlliance().isWhite()) ||
                            (BoardUtils.EIGHTH_COLUMN[piece.getPiecePosition()] && piece.getPieceAlliance().isBlack())))
            {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(piece.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        if (piece.getPieceAlliance().isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new PawnAttackMove(board, piece, candidateDestinationCoordinate, pieceOnCandidate)));
                        }
                        else {
                            legalMoves.add(new PawnAttackMove(board, piece, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if (board.getEnPassantPawn() != null && board.getEnPassantPawn().getPiecePosition() ==
                        (piece.getPiecePosition() - (piece.getPieceAlliance().getOppositeDirection()))) {
                    final Piece pieceOnCandidate = board.getEnPassantPawn();
                    if (piece.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        legalMoves.add(new PawnEnPassantAttackMove(board, piece, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
}
