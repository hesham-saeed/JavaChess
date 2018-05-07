package com.chess.engine;

import com.chess.engine.gui.StartState;
import com.chess.engine.gui.StopState;
import com.chess.engine.gui.Table;

import java.awt.*;
import java.awt.event.KeyEvent;

public class JChess {
    static boolean paused = true;
    public static void main(String [] args){
        Table  table = Table.getInstance();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P){
                    if (e.getID() == KeyEvent.KEY_PRESSED)
                    {
                        if (paused == false){
                            StartState startState = new StartState();
                            startState.doAction(table);
                            paused = true;
                        }
                        else if (paused == true){
                            StopState stopState = new StopState();
                            stopState.doAction(table);
                            paused=false;
                        }
                    }

                }
                return false;

            }
        });


    }

}
