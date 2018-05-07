package com.chess.engine.gui;

import com.chess.engine.board.*;
import com.chess.engine.pieces.AI.MiniMax;
import com.chess.engine.pieces.AI.MoveStrategy;
import com.google.common.collect.Lists;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

public class Table extends Observable {


    public TakenPiecesPanel getTakenPiecesPanel() {
        return takenPiecesPanel;
    }
    public GameHistoryPanel getGameHistoryPanel() { return gameHistoryPanel; }
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    Board chessBoard;
    private final GameSetup gameSetup;
    public static BoardDirection boardDirection;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(800,800);
    public static boolean highlightLegalMoves;
    public final MoveLog moveLog;
    private Move computerMove;

    private boolean musicOn = true;
    AudioStream audioStream;
    AudioData audioData;
    ContinuousAudioDataStream audioLoop;

    private static Table INSTANCE = new Table();

    private Table(){
        this.gameFrame = new JFrame("Chess");
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        boardDirection=BoardDirection.NORMAL;
        chessBoard = Board.createStandardBoard();
        this.moveLog = new MoveLog();
        this.addObserver(new TableAIWatcher());
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.boardPanel = new BoardPanel(chessBoard);
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
        try {

            audioStream = new AudioStream(new FileInputStream("guitarup_full.wav"));
            audioData = audioStream.getData();
            audioLoop = new ContinuousAudioDataStream(audioData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Table getInstance(){
        return INSTANCE;
    }

    private Board getGameBoard(){
        return this.chessBoard;
    }

    public GameSetup getGameSetup() {
        return this.gameSetup;
    }

    private JMenuBar createTableMenuBar(){
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
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
                if (loadedBoard != null)
                    chessBoard = loadedBoard;
                TilePanel.setChessBoard(chessBoard);
                //chessBoard = GameSaver.loadGame();
                getBoardPanel().drawBoard(chessBoard);
                //Game.getInstance().redrawBoard(chessBoard);

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
                toggleMusic();
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

    private JMenu createOptionsMenu() {

        final JMenu optionsMenu = new JMenu("Options");
        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Table.getInstance().getGameSetup().promptUser();
                Table.getInstance().setUpdate(Table.getInstance().getGameSetup());
            }
        });
        optionsMenu.add(setupGameMenuItem);
        return optionsMenu;
    }

    public void setUpdate(final GameSetup gameSetup)
    {
        setChanged();
        notifyObservers(gameSetup);
    }

    private static class TableAIWatcher implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            if (Table.getInstance().getGameSetup().isAIPlayer(Table.getInstance().getGameBoard().currentPlayer())&&
                !Table.getInstance().getGameBoard().currentPlayer().isInCheckMate() &&
                !Table.getInstance().getGameBoard().currentPlayer().isInStaleMate()){
                //create AI thread and execute work
                System.out.println(Table.getInstance().getGameBoard().currentPlayer() + " is set to AI, thinking....");
                final AIThink think = new AIThink();
                think.execute();
            }

            if (Table.getInstance().getGameBoard().currentPlayer().isInCheckMate()){
                System.out.println("Game Over: Player " + Table.getInstance().getGameBoard().currentPlayer() + " is in checkmate!");
            }

            if (Table.getInstance().getGameBoard().currentPlayer().isInStaleMate()){
                System.out.println("Game Over: Player " + Table.getInstance().getGameBoard().currentPlayer() + " is in Stalemate!");
            }
        }
    }

    public void updateGameBoard(final Board board) {
        this.chessBoard = board;
    }

    public void updateComputerMove(final Move move){
        this.computerMove = move;
    }

    public void moveMadeUpdate(final Table.PlayerType playertype)
    {
        setChanged();
        notifyObservers(playertype);
    }

    public MoveLog getMoveLog(){
        return this.moveLog;
    }

    private class moveMusic implements Runnable{

        @Override
        public void run() {
            try {
                AudioStream audioStream = new AudioStream(new FileInputStream("move.wav"));
                AudioPlayer.player.start(audioStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void playMoveMusic(){
        new Table.moveMusic().run();
    }

    public void toggleMusic() {
        if (musicOn)
            AudioPlayer.player.start(audioLoop);
        else
            AudioPlayer.player.stop(audioLoop);


        musicOn = !musicOn;
    }


    private static class AIThink extends SwingWorker<Move, String>{

        private AIThink(){

        }

        @Override
        protected Move doInBackground() throws Exception {

            final MoveStrategy miniMax = new MiniMax(4);
            final Move bestMove = miniMax.execute(Table.getInstance().getGameBoard());
            return bestMove;
        }

        @Override
        protected void done() {
            try {
                final Move bestMove = get();

                Table.getInstance().updateComputerMove(bestMove);
                CareTaker.getInstance().add(Table.getInstance().chessBoard.saveMemento());
                Board newBoard = Table.getInstance().getGameBoard().currentPlayer().makeMove(bestMove).getTransitionBoard();
                Table.getInstance().updateGameBoard(newBoard);
                TilePanel.setChessBoard(Table.getInstance().getGameBoard());
                Table.getInstance().playMoveMusic();
                Table.getInstance().moveMadeUpdate(PlayerType.COMPUTER);
                Table.getInstance().getMoveLog().addMove(bestMove);
                Table.getInstance().getGameHistoryPanel().redo(Table.getInstance().getGameBoard(), Table.getInstance().getMoveLog());
                Table.getInstance().getTakenPiecesPanel().redo(Table.getInstance().getMoveLog());

                Table.getInstance().getBoardPanel().drawBoard(Table.getInstance().getGameBoard());





            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public enum PlayerType {
        HUMAN,
        COMPUTER
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
