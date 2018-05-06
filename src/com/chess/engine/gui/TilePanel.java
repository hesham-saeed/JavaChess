package com.chess.engine.gui;

import com.chess.engine.board.*;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class TilePanel extends JPanel {
    private final int tileCoordinate;
    private static final Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
    private static final String ICONS_ROOT_PATH = "art/simple/";
    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");
    public static Board chessBoard;
    //private BoardDirection boardDirection;
    private BoardPanel boardPanel;

    private static Tile sourceTile;
    private static Tile destinationTile;
    private static Piece humanMovedPiece;



    public TilePanel(final BoardPanel boardPanel,
                     final int tileId,
                     Board board){
        super(new GridBagLayout());
        this.tileCoordinate = tileId;

        this.chessBoard = board;
        this.boardPanel = boardPanel;
        //this.boardDirection = boardDirection;
        setPreferredSize(TILE_PANEL_DIMENSION);
        setTileBackgroundColor();
        setTileIcon(board);

        this.addMouseListener(new myMouseListener());
        setVisible(true);
        validate();
    }

    public static void setChessBoard(Board chessBoard){
        TilePanel.chessBoard = chessBoard;
    }

    public class myMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (sourceTile == null) {
                    sourceTile = chessBoard.getTile(tileCoordinate);
                    humanMovedPiece = sourceTile.getPiece();
                    if (humanMovedPiece == null) {
                        sourceTile = null;
                    }
                }
                else {
                    destinationTile = chessBoard.getTile(tileCoordinate);
                    //System.out.println("" + sourceTile.getTileCoordinate() + "," + destinationTile.getTileCoordinate());

                    final Move move = MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),
                                                             destinationTile.getTileCoordinate());

                    final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);

                    if (transition.getMoveStatus().isDone()) {
                        Game.getInstance().addMoveToLog(move);
                        Game.getInstance().playMoveMusic();
                        CareTaker.getInstance().add(chessBoard.saveMemento());
                        chessBoard = transition.getTransitionBoard();
                    }
                    sourceTile = null;
                    destinationTile = null;
                    humanMovedPiece = null;

                }
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Game.getInstance().redrawBoard(chessBoard);
                    }
                });
            }


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }



    public void drawTile(Board chessBoard){
        setTileBackgroundColor();
        setTileIcon(chessBoard);
        highlightLegals(chessBoard);
        validate();
        repaint();
    }

    private void setTileBackgroundColor() {
        if ((tileCoordinate / 8 ) % 2 == 0){
            setBackground(tileCoordinate % 2 == 0 ? lightTileColor : darkTileColor);
        }
        else {
            setBackground(tileCoordinate % 2 == 0 ? darkTileColor : lightTileColor);
        }
    }

    private void setTileIcon(final Board board){
        this.removeAll();
        if (board.getTile(this.tileCoordinate).isTileOccupied()){
            try {
                final BufferedImage image =
                        ImageIO.read(new File(ICONS_ROOT_PATH
                                + board.getTile(this.tileCoordinate).getPiece()
                                .getPieceAlliance().toString().substring(0,1)
                                + board.getTile(this.tileCoordinate).getPiece().toString() + ".gif"));
                this.add(new JLabel(new ImageIcon(image)));
            }
            catch (IOException e ){
                e.printStackTrace();
            }
        }
    }

    private void highlightLegals(final Board board) {
        if (Table.highlightLegalMoves) {
            for (final Move move : pieceLegalMoves(board)) {
                if (move.getDestinationCoordinate() == this.tileCoordinate) {
                    try {
                        add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                    }
                    catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private Collection<Move> pieceLegalMoves(final Board board) {
        if(humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
            return humanMovedPiece.calculateLegalMoves(board);
        }
        return Collections.emptyList();
    }
}
