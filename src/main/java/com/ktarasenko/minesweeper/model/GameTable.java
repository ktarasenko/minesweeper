package com.ktarasenko.minesweeper.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.ktarasenko.minesweeper.util.Point;

public class GameTable {

    public static final int DEFAULT_HEIGHT = 8;
    public static final int DEFAULT_WIDTH = 8;
    public static final int DEFAULT_MINES = 10;

    public static final int MAX_SIZE = 10;

    private final State[][] table;
    private TableGenerator generator;

    public enum State {
       CLOSED, CLOSED_MINE, CHEATING_MINE, EXPLODED_MINE, MINE, EMPTY, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT
    }

    public GameTable(int width, int height, int mines){
      this(new TableGenerator(width, height, mines));
    }

    public GameTable(TableGenerator generator){
       table = new State[generator.getWidth()][generator.getHeight()];
       for (int i = 0; i < table.length; i++){
           for (int j = 0; j < table[i].length; j++){
               table[i][j] = State.CLOSED;
           }
       }
       for (int i =0; i < generator.getMinesCount(); i++){
           Point mine =  generator.next();
           table[mine.first][mine.second] = State.CLOSED_MINE;
       }
       this.generator = generator;
    }

    public GameTable(Parcel state){
        int width = state.readInt();
        int height = state.readInt();
        int mines = state.readInt();
        generator = new TableGenerator(width, height, mines);
        table = new State[width][height];
    }

    public boolean open(int x, int y){
        State s = table[x][y];
        if (s == State.CLOSED_MINE || s == State.CHEATING_MINE){
            table[x][y] = State.EXPLODED_MINE;
            return false;
        } else {
            int minesCount = getNeighborMines(x, y);
            table[x][y] = State.values()[State.EMPTY.ordinal() + minesCount];
            if (minesCount == 0){
                for (Point p : generator.getNeighbors(x, y)){
                    if (table[p.first][p.second] == State.CLOSED){
                        open(p.first, p.second);
                    }
                }
            }
            return true;
        }
    }

    private int getNeighborMines(int x, int y) {
       int mines = 0;
       for (Point p : generator.getNeighbors(x, y)){
           State s = table[p.first][p.second];
           if (s == State.CLOSED_MINE || s == State.CHEATING_MINE){
               mines++;
           }
       }
       return mines;
    }

    public State get(int x, int y){
        State s = table[x][y];
        return s == State.CLOSED_MINE? State.CLOSED : s;
    }

    public boolean check(){
        for (int i = 0; i < table.length; i++){
            for (int j = 0; j < table[i].length; j++){
                if (table[i][j] == State.CLOSED){
                    return false;
                }
            }
        }
        return true;
    }

    public void cheat(){
        for (int i = 0; i < table.length; i++){
            for (int j = 0; j < table[i].length; j++){
                if (table[i][j] == State.CLOSED_MINE){
                    table[i][j] = State.CHEATING_MINE;
                }
            }
        }
    }

    public void saveState(Parcel outState){

    }


}
