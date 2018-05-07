package com.chess.engine.pieces.MoveStrategyForPieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.List;

public abstract class MoveStrategyPiece {


    protected final int[] CANDIDATE_MOVE_VECTOR_COORDENATES;

    protected MoveStrategyPiece(int[] candidate_move_vector_coordenates) {
        CANDIDATE_MOVE_VECTOR_COORDENATES = candidate_move_vector_coordenates;
    }

    public abstract List<Move> calculateLegalMoves(final Board board, Piece piece);

}
