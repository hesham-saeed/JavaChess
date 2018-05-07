package com.chess.engine.board;

import com.chess.engine.gui.MoveLog;
import com.chess.engine.gui.Table;

import java.io.*;

public class GameSaver {

    public static void saveGame(Board savedBoard, MoveLog moveLog){

        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("data.bin"))){

            os.writeObject(savedBoard);
            os.writeObject(moveLog);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean loadGame() {
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream("data.bin"))){

            Board board = (Board) is.readObject();
            MoveLog moveLog = (MoveLog)is.readObject();
            if (board != null && moveLog != null){
                Table.getInstance().setChessBoard(board);
                Table.getInstance().setMoveLog(moveLog);
                return true;
            }


        } catch (IOException | ClassNotFoundException e) {
            System.out.println("no saved game yet");
            return false;
        }
        return false;
    }
}
