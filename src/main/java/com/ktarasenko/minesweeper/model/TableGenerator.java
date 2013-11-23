package com.ktarasenko.minesweeper.model;

import com.ktarasenko.minesweeper.util.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class TableGenerator {

    private final int height;
    private final int width;
    private final int minesCount;
    private Iterator<Point> iterator;

    public TableGenerator(){
        this(GameTable.DEFAULT_WIDTH, GameTable.DEFAULT_HEIGHT, GameTable.DEFAULT_MINES);
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

    public Point next(){
        if (iterator == null){
            ArrayList<Point> seq = new ArrayList<Point>(width * height);
            for (int i = 0; i < width; i++){
                for (int j = 0; j < height; j++){
                    seq.add(new Point(i, j));
                }
            }
            Collections.shuffle(seq, new Random());
            iterator = seq.subList(0, minesCount).iterator();
        }
        return iterator.next();
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

    public ArrayList<Point> getNeighbors(int x, int y){
        ArrayList<Point> list = new ArrayList<Point>();
        addPoint(list, x - 1, y);
        addPoint(list, x - 1, y + 1);
        addPoint(list, x - 1, y - 1);
        addPoint(list, x + 1, y);
        addPoint(list, x + 1, y + 1);
        addPoint(list, x + 1, y - 1);
        addPoint(list, x, y + 1);
        addPoint(list, x, y - 1);
        return list;
    }

    private void addPoint(ArrayList<Point> list, int x, int y) {
       if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()){
           list.add(new Point(x, y));
       }
    }
}
