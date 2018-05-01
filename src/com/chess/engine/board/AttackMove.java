package com.chess.engine.board;

import com.chess.engine.pieces.*;

public class AttackMove extends Move{
        final Piece attackedPiece;

        public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, final Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

    @Override
    public Board execute() {
        return null;
    }

    @Override
    public boolean isAttack() {
            return true;
    }

    @Override
    public Piece getAttackedPiece(){
            return this.attackedPiece;
    }

    @Override
    public int hashCode() {
            return this.attackedPiece.hashCode() * super.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
            if(this == other) {
                return true;
            }
            else if(!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
    }

}