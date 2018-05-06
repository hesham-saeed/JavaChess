package com.chess.engine.gui;

import com.chess.engine.board.*;
import sun.audio.*;

import javax.sound.midi.SysexMessage;
import java.io.FileInputStream;
import java.io.IOException;

public class Game {
    private boolean musicOn = true;
    AudioStream audioStream;
    AudioData audioData;
    ContinuousAudioDataStream audioLoop;


    public void toggleMusic() {
        if (musicOn)
            AudioPlayer.player.start(audioLoop);
        else
            AudioPlayer.player.stop(audioLoop);


        musicOn = !musicOn;
    }

    private BoardGUI boardGUI;
    private final MoveLog moveLog;

    private static Game sGame;

    public static Game getInstance(){
        if (sGame == null){
            sGame = new Game();
        }
        return sGame;
    }

    private Game(){
        moveLog = new MoveLog();
        initializeGame();
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
        new moveMusic().run();
    }


    public void addMoveToLog(Move move){
        moveLog.addMove(move);

    }

    public void redrawBoard(Board chessBoard){
        boardGUI.getBoardPanel().drawBoard(chessBoard);
        boardGUI.getGameHistoryPanel().redo(chessBoard, moveLog);
        boardGUI.getTakenPiecesPanel().redo(moveLog);
    }

    public void initializeGame(){
        boardGUI = new BoardGUI();
        try {

            audioStream = new AudioStream(new FileInputStream("guitarup_full.wav"));
            audioData = audioStream.getData();
            audioLoop = new ContinuousAudioDataStream(audioData);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
