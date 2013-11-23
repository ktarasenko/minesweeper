package com.ktarasenko.minesweeper.model;

import com.ktarasenko.minesweeper.util.Pair;

public class TableGenerator {

    private int minesCount;

    public TableGenerator(){

    }

    public TableGenerator(int width, int height, int mines) {

    }

    public Pair<Integer, Integer> next(){
        return Pair.create(0,0);
    }

    public int getMinesCount() {
        return minesCount;
    }
}
