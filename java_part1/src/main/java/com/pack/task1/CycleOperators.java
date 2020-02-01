package com.pack.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CycleOperators {

    private static final Scanner in = new Scanner(System.in);

    static void task1_DividedBy3NotBy5() {
        try {
            System.out.print("N = ");
            int n = Integer.parseInt(in.nextLine().trim());
            getDividedBy3NotBy5(n).forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void task2_NumbersDividedBy5() {
        try {
            System.out.print("N = ");
            int n = Integer.parseInt(in.nextLine().trim());
            getNumbersDividedByXToN(5, n).forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void task3_IsPowOf2() {
        try {
            System.out.print("N = ");
            int n = Integer.parseInt(in.nextLine().trim());
            System.out.println(String.format("%s is pow of 2: %s", n, isPowOf(n, 2)));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void task4_FibonacciNumbers() {
        try {
            System.out.print("N = ");
            int n = Integer.parseInt(in.nextLine().trim());
            getFibonacciNumbers(n).forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static List<Integer> getDividedBy3NotBy5(int n) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 3; i < n; i++) {
            if (isDividedBy3NotBy5(i) && isDividedBy3NotBy5(getSumOfNums(i))) {
                numbers.add(i);
            }
        }
        return numbers;
    }

    static int getSumOfNums(int x) {
        int sum = 0;
        while (x > 0) {
            sum += x % 10;
            x = x / 10;
        }
        return sum;
    }

    static boolean isDividedBy3NotBy5(int x) {
        return isDividedBy(x, 3) && !isDividedBy(x, 5);
    }

    static boolean isDividedBy(int x, int y) {
        return x % y == 0;
    }

    static List<Integer> getNumbersDividedByXToN(int x, int n) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = x; i < n; i++) {
            if (isDividedBy(i, x)) {
                numbers.add(i);
            }
        }
        return numbers;
    }

    static boolean isPowOf(int x, int pow) {
        if (x <= 0) {
            return false;
        }
        int ost = 0;
        while (ost == 0) {
            ost = x % pow;
            x = x / pow;
        }
        return x == 0;
    }

    static List<Integer> getFibonacciNumbers(int n) {
        List<Integer> numbers = new ArrayList<>();
        if (n <= 1) {
            return numbers;
        }
        numbers.add(1);
        int nextNumber = 1;
        int i = 0;
        do {
            numbers.add(nextNumber);
            nextNumber = numbers.get(i) + numbers.get(i + 1);
            i++;
        } while (nextNumber < n);
        return numbers;
    }


    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1 - task 1");
            System.out.println("2 - task 2");
            System.out.println("3 - task 3");
            System.out.println("4 - task 4");
            System.out.println("other - exit\n\nTask: ");
            switch (in.nextLine().trim()) {
                case "1": {
                    task1_DividedBy3NotBy5();
                    break;
                }
                case "2": {
                    task2_NumbersDividedBy5();
                    break;
                }
                case "3": {
                    task3_IsPowOf2();
                    break;
                }
                case "4": {
                    task4_FibonacciNumbers();
                    break;
                }
                default: {
                    return;
                }
            }
        }
    }

}

