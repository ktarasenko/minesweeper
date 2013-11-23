package com.ktarasenko.minesweeper.model;

import android.os.Parcelable;

public class GameTable {

    public static final int DEFAULT_HEIGHT = 8;
    public static final int DEFAULT_WIDTH = 8;
    public static final int DEFAULT_MINES = 10;

    public static final int MAX_SIZE = 10;

    public enum State {
       CLOSED, CHEATING_MINE, EXPLODED_MINE, MINE, EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    public GameTable(int width, int height, int mines){
      this(new TableGenerator(width, height, mines));
    }

    public GameTable(TableGenerator generator){

    }

    public GameTable(Parcelable state){

    }

    public boolean open(int x, int y){
        return false;
    }

    public State get(int x, int y){
        return State.CLOSED;
    }

    public boolean check(){
        return false;
    }

    public void cheat(){

    }

    public Parcelable saveState(){
        return null;
    }


}
