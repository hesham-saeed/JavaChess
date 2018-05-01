package com.chess.engine.board;

public class MoveFactory {

     static MoveFactory moveFactory = new MoveFactory();

    private MoveFactory () {
        throw new RuntimeException("Not instantiatable");
    }

    public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {

        for(final Move move : board.getAllLegalMoves()) {
            if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
                return move;
            }
        }
        return Move.NULL_MOVE;
    }

    public static MoveFactory getMoveFactory(){
        return moveFactory;
    }
}
