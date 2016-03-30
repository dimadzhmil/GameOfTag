package com.example.android.gameoftag.models;

/**
 * Created by User on 26.03.2016.
 */
public class DimensionalArray {
    private final int[][] doubleArray;

    public DimensionalArray(int[][] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public int[] toSingleDimension() {
        int[] result = new int[] {1, 1};
        return result;
    }
}
