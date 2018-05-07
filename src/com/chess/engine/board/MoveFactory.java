package com.chess.engine.board;

public class MoveFactory {

    //static MoveFactory moveFactory = new MoveFactory();

    private MoveFactory () {
        throw new RuntimeException("Not instantiatable");
    }

    public static Move createMove(final Board board, final int currentCoordinate, final int destinationCoordinate) {

        /*for (int i=0;i<64;i++){
            if (i%8 == 0)
                System.out.println("");
            System.out.print(i +"\t");
        }
        System.out.println("");*/
        for(final Move move : board.getAllLegalMoves()) {
            //System.out.println("comparing to: " + move.getCurrentCoordinate() + ", " +  move.getDestinationCoordinate());
            if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
                return move;
            }
        }
        return Move.NULL_MOVE;
    }

    /*public static MoveFactory getMoveFactory(){
        return moveFactory;
    }*/
}
