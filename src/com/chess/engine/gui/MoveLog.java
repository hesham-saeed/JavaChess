package com.chess.engine.gui;

import java.io.Serializable;
import java.util.ArrayList;
import com.chess.engine.board.*;
import java.util.List;

public class MoveLog implements Serializable {

    private final List<Move> moves;

    MoveLog() {
        this.moves = new ArrayList<>();
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public void addMove(final Move move) {
        this.moves.add(move);
    }

    public int sizr(){
        return this.moves.size();
    }

    public void clear(){
        this.moves.clear();
    }

    public Move removeMove(int index){
        return this.moves.remove(index);
    }

    public boolean removeMove(final Move move){
        return this.moves.remove(move);
    }

    public int size() {
        return this.moves.size();
    }
}
