package com.chess.engine.gui;


public class StartState implements State {
    //
    @Override
    public void doAction(Table table) {
        table.setState(this);

        table.getGameFrame().setFocusableWindowState(true);
        table.getDialog().setVisible(false);

    }
}
