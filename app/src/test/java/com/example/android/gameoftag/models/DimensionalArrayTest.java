package com.example.android.gameoftag.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by User on 26.03.2016.
 */
public class DimensionalArrayTest {

    @Test
    public void testToSingleDimension() throws Exception {
        int[][] array = new int[2][1];
        array[0][0] = 2;
        array[1][0] = 2;

        DimensionalArray dimensionalArray = new DimensionalArray(array);
        int[] result = dimensionalArray.toSingleDimension();

        int[] expected = new int[2];
        expected[0] = 2;
        expected[1] = 2;

        assertArrayEquals(expected, result);
    }
}