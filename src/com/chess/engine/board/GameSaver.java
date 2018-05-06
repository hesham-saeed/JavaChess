package com.chess.engine.board;

import java.io.*;

public class GameSaver {

    public static void saveGame(Board savedBoard){

        try(ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("data.bin"))){

            os.writeObject(savedBoard);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Board loadGame() {
        try(ObjectInputStream is = new ObjectInputStream(new FileInputStream("data.bin"))){

            Board board = (Board) is.readObject();
            return board;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
