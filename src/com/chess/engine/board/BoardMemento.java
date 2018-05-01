package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

import java.util.Collection;
import java.util.List;

public class BoardMemento {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

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
