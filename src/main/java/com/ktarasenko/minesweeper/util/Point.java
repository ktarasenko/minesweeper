package com.ktarasenko.minesweeper.util;

/**
 * Synonym for Pair<Integer, Integer>
 */
public class Point extends Pair<Integer, Integer> {
    /**
     * Constructor for a Pair. If either are null then equals() and hashCode() will throw
     * a NullPointerException.
     *
     * @param first  the first object in the Pair
     * @param second the second object in the pair
     */
    public Point(Integer first, Integer second) {
        super(first, second);
    }
}
