package com.chess.engine.gui;

import javax.swing.*;

public class StartState implements State {
    //
    @Override
    public void doAction(Table table) {
        table.getGameFrame().setFocusableWindowState(true);
        table.getDialog().setVisible(false);

    }
}
