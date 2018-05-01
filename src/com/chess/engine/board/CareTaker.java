package com.chess.engine.board;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<BoardMemento> mementoList = new ArrayList<>();

    private static CareTaker sCareTaker;

    private CareTaker() {
    }

    public static CareTaker getInstance(){
        if (sCareTaker == null)
            sCareTaker = new CareTaker();
        return sCareTaker;
    }

    public void add(BoardMemento boardMemento){
        mementoList.add(boardMemento);
    }

    public BoardMemento get(int index){
        return mementoList.get(index);
    }

    public BoardMemento getLastMemento(){
        if (mementoList.size() > 0 ){
            return mementoList.get(mementoList.size()-1);
        }
        return null;
    }
}
