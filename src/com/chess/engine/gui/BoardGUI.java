package com.chess.engine.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardMemento;
import com.chess.engine.board.CareTaker;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BoardGUI {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;
    public static BoardDirection boardDirection;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(800,800);
    public static boolean highlightLegalMoves;

    public BoardGUI(){
        chessBoard=Board.createStandardBoard();

        this.gameFrame = new JFrame("Chess");
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        boardDirection=BoardDirection.NORMAL;
        this.boardPanel = new BoardPanel(chessBoard);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar(){
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that pgn file!");
            }
        });
        fileMenu.add(openPGN);


        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }


    private JMenu createPreferencesMenu(){
        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);

        preferencesMenu.addSeparator();

        final JCheckBoxMenuItem cbLegalMoveHighlighter = new JCheckBoxMenuItem(
                "Highlight Legal Moves", false);

        cbLegalMoveHighlighter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                highlightLegalMoves = cbLegalMoveHighlighter.isSelected();
            }
        });

        preferencesMenu.add(cbLegalMoveHighlighter);

        final JMenuItem undoActionMenuItem = new JMenuItem("Undo last move");
        undoActionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CareTaker careTaker = CareTaker.getInstance();
                //BoardMemento b = CareTaker.getInstance().getLastMemento();
                chessBoard.getStateFromMemento(careTaker.getLastMemento());
                TilePanel.chessBoard = chessBoard;
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(undoActionMenuItem);

        return preferencesMenu;
    }


    public enum BoardDirection{
        NORMAL{
            @Override
            java.util.List<TilePanel> traverse(java.util.List<TilePanel> boardTiles) {
                return boardTiles;
            }

            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }
        },
        FLIPPED{
            @Override
            java.util.List<TilePanel> traverse(java.util.List<TilePanel> boardTiles) {
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };
        abstract java.util.List<TilePanel> traverse(final List<TilePanel > boardTiles);
        abstract BoardDirection opposite();
    }

}
