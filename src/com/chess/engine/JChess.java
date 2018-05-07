package com.chess.engine;

import com.chess.engine.gui.StartState;
import com.chess.engine.gui.StopState;
import com.chess.engine.gui.Table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Scanner;

public class JChess implements KeyListener {
    static int i = 1;
    static boolean paused = false;
    public static void main(String [] args){
        Table  table = Table.getInstance();

//
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

/*
        System.out.println(table.getState().toString());
        StopState stopState = new StopState();
        Scanner scanner = new Scanner(System.in);
        while (true){
            if (scanner.next() == "p"){
                StartState startState = new StartState();
                startState.doAction(table);
            }
        }*/

    }
    public static void test(){

    }

    public static void test2(){

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_P){
            System.out.println("hahahahahahahahahha");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
