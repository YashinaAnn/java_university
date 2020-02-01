package com.pack.task1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.pack.task1.ConditionOperators.*;
import static junit.framework.TestCase.*;

public class ConditionOperatorsTest {

    private static final List<Integer> numbers = Arrays.asList(1, 2, 3, 12, -3, 4, -6, 7);

    @Test
    public void test_SquareOfSumGreaterThanSumOfSquares() {
        assertTrue(ConditionOperators.isSumOfSquaresGreater(10, 1) < 0);
    }

    @Test
    public void test_SquareOfSumEqualsSumOfSquares() {
        assertEquals(0, ConditionOperators.isSumOfSquaresGreater(1, 0), 0.1);
    }

    @Test
    public void test_salaryIncrease2Low() {
        assertEquals(600, getSalaryIncrease(30000, 2), 0.01);
    }

    @Test
    public void test_salaryIncrease2Upper() {
        assertEquals(600, getSalaryIncrease(30000, 4), 0.01);
    }

    @Test
    public void test_salaryIncrease5Low() {
        assertEquals(1500, getSalaryIncrease(30000, 5), 0.01);
    }

    @Test
    public void test_salaryIncrease5Upper() {
        assertEquals(1500, getSalaryIncrease(30000, 9), 0.01);
    }

    @Test
    public void test_salaryNotIncreaseLeft() {
        assertEquals(0, getSalaryIncrease(30000, 1), 0.01);
    }

    @Test
    public void test_salaryNotIncreaseRight() {
        assertEquals(0, getSalaryIncrease(30000, 10), 0.01);
    }

    @Test
    public void test_task3FirstFurther(){
        assertTrue(isFurther(5, 5, 2, 2) > 0);
    }

    @Test
    public void test_task3SecondFurther(){
        assertTrue(isFurther(1, 1, 2, 2) < 0);
    }

    @Test
    public void test_task3DistancesEqual(){
        assertEquals(0, isFurther(-1, 2, 1, 2), 0.01);
    }

    @Test
    public void testRightTrianglePositive() {
        assertTrue(isRightTriangle(3, 4, 5));
    }

    @Test
    public void testRightTriangleSidesEqual() {
        assertFalse(isRightTriangle(4, 4, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRightTriangleMaxSide() {
        assertFalse(isRightTriangle(10, 4, 5));
    }

    @Test
    public void testNotRightTriangle() {
        assertFalse(isRightTriangle(3, 4, 6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotRightTriangleNegativeSide() {
        assertFalse(isRightTriangle(3, 4, -1));
    }

    @Test
    public void testGetSeasonByMonthPositive() {
        assertEquals("Winter", getSeasonByMonth(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSeasonByMonthUnknownMonth() {
       getSeasonByMonth(13);
    }

}
