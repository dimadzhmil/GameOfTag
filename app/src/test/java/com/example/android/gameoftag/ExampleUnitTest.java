package com.example.android.gameoftag;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, sum(2, 2));
    }


    private int sum(int a, int b) {
        return a + b;
    }
}