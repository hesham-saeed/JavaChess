package com.chess.engine.board;



import java.io.Serializable;
import java.util.*;
import com.chess.engine.pieces.*;
import com.chess.engine.*;
import com.chess.engine.player.* ;
import com.google.common.collect.Iterables;
//import com.chess.engine.player.WhitePlayer;

public class Board implements Serializable {

    private List<Tile> gameBoard;
    private Collection<Piece> whitePieces;
    private Collection<Piece> blackPieces;

    private WhitePlayer whitePlayer;
    private BlackPlayer blackPlayer;
    private Player currentPlayer;

    private final Pawn enPassantPawn;

    Board(final Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);
        this.enPassantPawn = builder.enPassantPawn;
        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

       this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
       this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
       this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    private Collection<Piece> calculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        for (final Tile tile: gameBoard){
            if (tile.isTileOccupied()){
                final Piece piece =  tile.getPiece();
                if (piece.getPieceAlliance() == alliance){
                    activePieces.add(piece);
                }
            }
        }
        return Collections.unmodifiableList(activePieces);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i=0;i<BoardUtils.NUM_TILES;i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if ((i+1)%BoardUtils.NUM_TILES_PER_ROW == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Collection<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    public Player currentPlayer() {
        return this.currentPlayer;
    }

    public Collection<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces){
        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece: pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return Collections.unmodifiableList(legalMoves);
    }

    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }
    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];
        for (int i =0;i<BoardUtils.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return Collections.unmodifiableList(Arrays.asList(tiles));
    }
    public static Board createStandardBoard(){
        final Builder builder = new Builder();
        PieceFactoryMaker factoryMaker = PieceFactoryMaker.getInstance();
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.ROOK, Alliance.BLACK, 0, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.KNIGHT, Alliance.BLACK, 1, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.BISHOP, Alliance.BLACK, 2, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.QUEEN, Alliance.BLACK, 3, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.KING, Alliance.BLACK, 4, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.BISHOP, Alliance.BLACK, 5, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.KNIGHT, Alliance.BLACK, 6, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.ROOK, Alliance.BLACK, 7, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 8, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 9, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 10, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 11, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 12, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 13, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 14, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.BLACK, 15, true));
        // White Layout
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 48, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 49, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 50, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 51, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 52, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 53, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 54, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.PAWN, Alliance.WHITE, 55, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.ROOK, Alliance.WHITE, 56, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.KNIGHT, Alliance.WHITE, 57, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.BISHOP, Alliance.WHITE, 58, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.QUEEN, Alliance.WHITE, 59, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.KING, Alliance.WHITE, 60, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.BISHOP, Alliance.WHITE, 61, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.KNIGHT, Alliance.WHITE, 62, true));
        builder.setPiece(factoryMaker.choosePieceType(Piece.PieceType.ROOK, Alliance.WHITE, 63, true));

        builder.setMoveMaker(Alliance.WHITE);
        return builder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                this.blackPlayer.getLegalMoves()));
    }

    public Player blackPlayer() {
        return this.blackPlayer;
    }

    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public BoardMemento saveMemento(){
        return new BoardMemento(gameBoard, whitePieces, blackPieces,whitePlayer,blackPlayer,currentPlayer);
    }

    public Pawn getEnPassantPawn(){
        return this.enPassantPawn;
    }

    public void getStateFromMemento(BoardMemento boardMemento){
        this.gameBoard = boardMemento.getGameBoard();
        this.whitePieces = boardMemento.getWhitePieces();
        this.blackPieces = boardMemento.getBlackPieces();
        this.whitePlayer = boardMemento.getWhitePlayer();
        this.blackPlayer = boardMemento.getBlackPlayer();
        this.currentPlayer = boardMemento.getCurrentPlayer();
    }
}
