package com.chess.engine.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardMemento;
import com.chess.engine.board.CareTaker;
import com.chess.engine.board.GameSaver;
import com.google.common.collect.Lists;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Table {
    public GameHistoryPanel getGameHistoryPanel() {
        return gameHistoryPanel;
    }

    public TakenPiecesPanel getTakenPiecesPanel() {
        return takenPiecesPanel;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    Board chessBoard;
    public static BoardDirection boardDirection;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(800,800);
    public static boolean highlightLegalMoves;

    public Table(){
        this.gameFrame = new JFrame("Chess");
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        boardDirection=BoardDirection.NORMAL;
        chessBoard = Board.createStandardBoard();
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel(chessBoard);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar(){
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    public void setChessBoard(Board chessBoard) {
        this.chessBoard = chessBoard;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem saveGame = new JMenuItem("Save game");
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameSaver.saveGame(chessBoard);

            }
        });
        fileMenu.add(saveGame);


        final JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Board loadedBoard = GameSaver.loadGame();
                chessBoard = loadedBoard;
                TilePanel.setChessBoard(chessBoard);
                //chessBoard = GameSaver.loadGame();
                Game.getInstance().redrawBoard(chessBoard);

            }
        });
        fileMenu.add(loadGame);



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

        preferencesMenu.addSeparator();

        final JCheckBoxMenuItem toggleMusic = new JCheckBoxMenuItem(
                "Play Music", false);

        toggleMusic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                Game.getInstance().toggleMusic();
            }
        });

        preferencesMenu.add(toggleMusic);



        final JMenuItem undoActionMenuItem = new JMenuItem("Undo last move");
        undoActionMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CareTaker careTaker = CareTaker.getInstance();
                if (!careTaker.emptyMemento()){
                    chessBoard.getStateFromMemento(careTaker.getLastMemento());
                    TilePanel.chessBoard = chessBoard;
                    boardPanel.drawBoard(chessBoard);
                }
                //BoardMemento b = CareTaker.getInstance().getLastMemento();

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