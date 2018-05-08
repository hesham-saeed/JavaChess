package com.chess.engine.gui;

import com.chess.engine.board.Move;
import com.chess.engine.player.Player;

public class MoveCommand {
    Player p;
    public MoveCommand(Player p){
        this.p = p;
    }

    public void exectue(Move move){
        p.makeMove(move);
    }
}
