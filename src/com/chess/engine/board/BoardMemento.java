package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.Collection;
import java.util.List;

public class BoardMemento {
    private List<Tile> gameBoard;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;

    private WhitePlayer whitePlayer;
    private BlackPlayer blackPlayer;
    private Player currentPlayer;

    public BoardMemento(List<Tile> gameBoard,
                        Collection<Piece> whitePieces,
                        Collection<Piece> blackPieces,
                        WhitePlayer whitePlayer,
                        BlackPlayer blackPlayer,
                        Player currentPlayer) {

        this.gameBoard = gameBoard;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentPlayer = currentPlayer;
    }

    public List<Tile> getGameBoard() {
        return gameBoard;
    }

    public Collection<Piece> getWhitePieces() {
        return whitePieces;
    }

    public Collection<Piece> getBlackPieces() {
        return blackPieces;
    }

    public WhitePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public BlackPlayer getBlackPlayer() {
        return blackPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
