package com.pack.task1;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.pack.task1.Matrix.*;
import static org.junit.Assert.assertEquals;

public class MatrixTest {

    private static final List<Integer> numbers = Arrays.asList(1, 2, 3, 12, -3, 4, -6, 7, 81);

    @Test
    public void testFilterDividedByPositive() {
        Set<Integer> evenNumbers = Matrix.filterDividedBy(numbers, 2);
        Set<Integer> expectedEvenNums = numbers.stream().filter(x -> x % 2 == 0).collect(Collectors.toSet());
        assertEquals(expectedEvenNums, evenNumbers);
    }

    @Test
    public void testFilterNotDividedByPositive() {
        Set<Integer> oddNumbers = Matrix.filterNotDividedBy(numbers, 2);
        Set<Integer> expectedOddNums = numbers.stream().filter(x -> x % 2 != 0).collect(Collectors.toSet());
        assertEquals(expectedOddNums, oddNumbers);
    }

    @Test
    public void testFilterDividedByXOrY() {
        Set<Integer> result = Matrix.filterDividedBy(numbers, 3, 9);
        Set<Integer> expectedResult = numbers.stream().filter(x -> x % 3 == 0).collect(Collectors.toSet());
        expectedResult.addAll(numbers.stream().filter(x -> x % 9 == 0).collect(Collectors.toSet()));
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFilterDividedByXOrY_EmptyResult() {
        assertEquals(new HashSet<>(), Matrix.filterDividedBy(numbers, 100, 17));
    }

    @Test
    public void testSimpleNumbers() {
        assertEquals(Arrays.asList(2, 1, 3, 5, 11),
                Matrix.filterSimpleNumbers(Arrays.asList(2, 1, 3,  4, 5, 6, 11)));
    }

    @Test
    public void testSimpleNumbers_EmptyResult() {
        assertEquals(0, Matrix.filterSimpleNumbers(Arrays.asList(44, 4, 6)).size());
    }

    @Test
    public void testLuckyNumbers() {
        assertEquals(Arrays.asList(112211, 911317),
                Matrix.filterLuckyNumbers(Arrays.asList(2, 4, 6, 111112, 112211, 911317)));
    }

    @Test
    public void testLuckyNumbers_EmptyResult() {
        assertEquals(0, Matrix.filterLuckyNumbers(Arrays.asList(2, 4, 6, 111112)).size());
    }

    @Test
    public void testNumberName_Positive() {
        assertEquals("Один", getNumberName(1));
        assertEquals("Девять", getNumberName(9));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberName_NegativeNumber() {
        getNumberName(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberName_BigNumber() {
        getNumberName(10);
    }

    @Test
    public void testGCDPositive(){
        assertEquals(4, getGCD(8, 12));
    }

    @Test
    public void testLCMPositive(){
        assertEquals(35, getLCM(5, 7));
    }

    @Test
    public void testGCDArrayPositive(){
        List<Integer> nums = new ArrayList<>();
        nums.addAll(Arrays.asList(24, 12, 4, 8));
        assertEquals(4, getGCDForArray(nums));
    }

    @Test
    public void testLCMArrayPositive(){
        List<Integer> nums = new ArrayList<>();
        nums.addAll(Arrays.asList(24, 12, 4, 8));
        assertEquals(24, getLCMForArray(nums));
    }





}
