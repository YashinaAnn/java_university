package com.pack.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParametricCycleOperator {

    private static final Scanner in = new Scanner(System.in);

    static void task1_2_AB() {
        try {
            System.out.print("A = ");
            int a = Integer.parseInt(in.nextLine().trim());
            System.out.print("B = ");
            int b = Integer.parseInt(in.nextLine().trim());

            List<Integer> nums = numbersBetweenIncludedAB(a, b);
            System.out.println("Numbers between A and B, included A and B\nCount: " + nums.size());
            nums.forEach(System.out::println);

            nums = numbersBetweenExcludedAB(a, b);
            System.out.println("Numbers between A and B, excluded A and B\nCount: " + nums.size());
            nums.forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void task3_4_PowsOfA() {
        try {
            System.out.print("A = ");
            int a = Integer.parseInt(in.nextLine().trim());
            System.out.print("N = ");
            int n = Integer.parseInt(in.nextLine().trim());

            System.out.println(String.format("%s^%s = %s\nPows of %s from 1 to %s", a, n, pow(a, n), a, n));
            getPowsToN(a, n).forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static List<Integer> numbersBetweenIncludedAB(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("A must be less than or equal to B!");
        }
        List<Integer> numbers = new ArrayList<>();
        for (int i = a; i <= b; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    static List<Integer> numbersBetweenExcludedAB(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException("A must be less than or equal to B!");
        }
        List<Integer> numbers = new ArrayList<>();
        for (int i = a + 1; i < b; i++) {
            numbers.add(i);
        }
        return numbers;
    }

    static double pow(double a, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Incorrect value of N");
        }
        double res = 1;
        for (int i = 1; i <= n; i++) {
            res *= a;
        }
        return res;
    }

    static List<Double> getPowsToN(double a, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Incorrect value of N");
        }
        List<Double> pows = new ArrayList<>();
        double res = 1;
        for (int i = 1; i <= n; i++) {
            res *= a;
            pows.add(res);
        }
        return pows;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1 - task 1, 2");
            System.out.println("2 - task 3, 4");
            System.out.println("other - exit\n\nTask: ");
            switch (in.nextLine().trim()) {
                case "1": {
                    task1_2_AB();
                    break;
                }
                case "2": {
                    task3_4_PowsOfA();
                    break;
                }
                default: {
                    return;
                }
            }
        }
    }

}
