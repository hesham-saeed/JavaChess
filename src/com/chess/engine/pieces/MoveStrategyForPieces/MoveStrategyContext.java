package com.chess.engine.pieces.MoveStrategyForPieces;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.List;


public class MoveStrategyContext {

    private MoveStrategyPiece moveStrategyPiece;

    public MoveStrategyContext(MoveStrategyPiece moveStrategyPiece) {

        this.moveStrategyPiece = moveStrategyPiece;

    }

    public List<Move> excuteCalculateLegalMoves(final Board board, Piece piece) {
       return this.moveStrategyPiece.calculateLegalMoves(board, piece);
    }
}
