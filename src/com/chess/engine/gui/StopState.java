package com.chess.engine.gui;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;

import javax.swing.*;

public class StopState implements State {
    @Override
    public void doAction(Table table) {
        //
        table.getDialog().setVisible(true);
        table.getGameFrame().setFocusableWindowState(false);
    }
}
