package com.chess.engine.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardPanel extends JPanel {
    final List<TilePanel> boardTiles;
    //private Board chessBoard;
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(400,400);
    BoardPanel(Board chessBoard){
        super(new GridLayout(8,8));
        this.boardTiles = new ArrayList<>();

        for (int i = 0; i< BoardUtils.NUM_TILES; i++){
            TilePanel tilePanel = new TilePanel(this, i, chessBoard);
            this.boardTiles.add(tilePanel);
            this.add(tilePanel);
        }
        setPreferredSize(BOARD_PANEL_DIMENSION);
        validate();
    }

    public void drawBoard(Board chessBoard) {
        removeAll();
        for (final TilePanel tilePanel: Table.boardDirection.traverse(boardTiles)){
            tilePanel.drawTile(chessBoard);
            add(tilePanel);
        }
        validate();
        repaint();
    }
}
