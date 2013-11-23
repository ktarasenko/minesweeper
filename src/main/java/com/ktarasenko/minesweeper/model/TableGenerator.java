package com.ktarasenko.minesweeper.model;

import com.ktarasenko.minesweeper.util.Pair;

public class TableGenerator {

    private final int height;
    private final int width;
    private final int minesCount;

    public TableGenerator(){
      this.height = GameTable.DEFAULT_HEIGHT;
      this.width = GameTable.DEFAULT_WIDTH;
      this.minesCount = GameTable.DEFAULT_MINES;
    }

    public TableGenerator(int width, int height, int mines) {
        if (width <= 0 || height <= 0
                || width > GameTable.MAX_SIZE
                || height > GameTable.MAX_SIZE
                || mines <= 0
                || mines > width*height)  {
            throw new IllegalArgumentException("Wrong table parameters");
        }
        this.width = width;
        this.height = height;
        this.minesCount = mines;
    }

    public Pair<Integer, Integer> next(){
        return Pair.create(0,0);
    }

    public int getMinesCount() {
        return minesCount;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
