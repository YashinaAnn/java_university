package com.pack.task1;

import org.junit.Test;

import java.util.Arrays;

import static com.pack.task1.CycleOperators.*;
import static junit.framework.TestCase.*;

public class CycleOperatorsTest {

    @Test
    public void testNotDividedBy5DividedBy3(){
        assertEquals(Arrays.asList(3, 6, 9, 12), getDividedBy3NotBy5(13));
    }

    @Test
    public void testDividedByPositive(){
        assertTrue(isDividedBy(20, 5));
    }

    @Test
    public void testDividedByNegative(){
        assertFalse(isDividedBy(20, 3));
    }

    @Test
    public void testSumOfNums() {
        assertEquals(4, getSumOfNums(103), 0.001);
    }

    @Test
    public void testNumbersDividedByXToN() {
        assertEquals(Arrays.asList(5, 10, 15, 20, 25), getNumbersDividedByXToN(5, 26));
    }

    @Test
    public void testPowOfNPositive() {
        assertTrue(isPowOf(125, 5));
    }

    @Test
    public void testPowOf2Positive() {
        assertTrue(isPowOf(1024, 2));
    }

    @Test
    public void testPowOf2Zero() {
        assertTrue(isPowOf(1, 2));
    }

    @Test
    public void testPowOf2Negative() {
        assertFalse(isPowOf(2014, 2));
    }

    @Test
    public void testFibonacciNumbers() {
        assertEquals(Arrays.asList(1, 1, 2, 3, 5, 8, 13, 21, 34), getFibonacciNumbers(35));
    }

    @Test
    public void testFibonacciNumbersUpperEdge() {
        assertEquals(Arrays.asList(1, 1, 2, 3), getFibonacciNumbers(5));
    }

}
