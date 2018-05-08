package com.chess.engine.gui;


public class StopState implements State {
    @Override
    public void doAction(Table table) {
        table.setState(this);
        table.getDialog().setVisible(true);
        table.getGameFrame().setFocusableWindowState(false);
    }
}
