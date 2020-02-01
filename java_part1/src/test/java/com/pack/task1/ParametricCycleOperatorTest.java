package com.pack.task1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.pack.task1.ParametricCycleOperator.*;
import static org.junit.Assert.assertEquals;

public class ParametricCycleOperatorTest {

    @Test
    public void testNumbersBetweenIncludedABPositive() {
        assertEquals(numbersBetweenIncludedAB(1, 4), Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void testNumbersBetweenIncludedABOneNumber() {
        assertEquals(numbersBetweenIncludedAB(1, 1), Arrays.asList(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumbersBetweenIncludedABIncorrectOrder() {
        numbersBetweenIncludedAB(6, 4);
    }

    @Test
    public void testNumbersBetweenExcludedABPositive() {
        assertEquals(numbersBetweenExcludedAB(1, 1), new ArrayList<>());
    }

    @Test
    public void testNumbersBetweenExcludedABEmptyList() {
        assertEquals(numbersBetweenExcludedAB(1, 1), new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumbersBetweenExcludedABIncorrectOrder() {
        numbersBetweenIncludedAB(6, 4);
    }

    @Test
    public void testPowPositive() {
        assertEquals(8.0, pow(2, 3), 0.001);
    }

    @Test
    public void testPowZero() {
        assertEquals(1.0, pow(2, 0), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPowNegative() {
        pow(2, -1);
    }

    @Test
    public void testPowToN() {
        assertEquals(Arrays.asList(2.0, 4.0, 8.0, 16.0, 32.0), getPowsToN(2, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPowToNNegative() {
        getPowsToN(2, -1);
    }
}
