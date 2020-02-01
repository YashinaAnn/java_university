package com.pack.task1;

import java.util.*;
import java.util.stream.Collectors;

import static com.pack.task1.CycleOperators.isDividedBy;
import static java.lang.String.format;

public class Matrix {

    private static final Scanner in = new Scanner(System.in);

    private static String[] numbersNames = new String[] {"Ноль", "Один", "Два", "Три",
            "Четыре", "Пять", "Шесть", "Семь", "Восемь", "Девять" };


    public static void task1_OddEvenNumbers(){
        List<Integer> numbers = readArray();
        System.out.println("Odd numbers: ");
        filterNotDividedBy(numbers, 2).forEach(System.out::println);
        System.out.println("Even numbers: ");
        filterDividedBy(numbers, 2).forEach(System.out::println);
    }

    public static void task2_DividedBy3Or9(){
        List<Integer> numbers = readArray();
        System.out.println("Divided by 3 or by 9: ");
        Set<Integer> result = filterDividedBy(numbers, 3, 9);
        result.forEach(System.out::println);
    }

    public static void task3_DividedBy5Or10() {
        List<Integer> numbers = readArray();
        System.out.println("Divided by 5 or by 10: ");
        Set<Integer> result = filterDividedBy(numbers, 5, 10);
        result.forEach(System.out::println);
    }

    public static void task4_GcdLcmForArray() {
        List<Integer> numbers = readArray();
        System.out.println(format("GCD = %s", getGCDForArray(numbers)));
        System.out.println(format("LCM = %s", getLCMForArray(numbers)));
    }

    public static void task5_SimpleNumbers() {
        List<Integer> numbers = readArray();
        System.out.println("Simple numbers: ");
        filterSimpleNumbers(numbers).forEach(System.out::println);
    }

    public static void task6_LuckyNumbers() {
        List<Integer> numbers = readArray();
        System.out.println("Happy numbers: ");
        filterLuckyNumbers(numbers).forEach(System.out::println);
    }

    public static void task7_NumberName() {
        try {
            System.out.print("n = ");
            int n = in.nextInt();
            System.out.println(getNumberName(n));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<Integer> readArray() {
        List<Integer> numbers = new ArrayList<>();
        System.out.print("N = ");
        try {
            int n = Integer.parseInt(in.nextLine().trim());
            for (int i = 0; i < n; i++) {
                System.out.print(format("a[%s] = ", i));
                numbers.add(in.nextInt());
            }
            return numbers;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    static int getGCD(int a, int b){
        return b == 0 ? a : getGCD(b,a % b);
    }

    static int getLCM(int a, int b){
        return a / getGCD(a,b) * b;
    }

    static int getGCDForArray(List<Integer> numbers) {
        int divider = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            divider = getGCD((divider < numbers.get(i) ? divider : numbers.get(i)),
                    (divider < numbers.get(i) ? numbers.get(i) : divider));
        }
        return divider;
    }

    static int getLCMForArray(List<Integer> numbers) {
        int multiple = 1;
        for (int i = 0; i < numbers.size(); i++) {
            multiple = getLCM(multiple, numbers.get(i));
        }
        return multiple;
    }

    static String getNumberName(int n) {
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("Number should be less than 10 and greater than 0");
        }
        return numbersNames[n];
    }

    static boolean isSimpleNumber(int x) {
        for (int i = 2; i < x; i++) {
            if (isDividedBy(x, i)) {
                return false;
            }
        }
        return true;
    }

    static Set<Integer> filterDividedBy(Collection<Integer> array, Integer... dividers) {
        Set<Integer> result = new HashSet<>();
        for (int divider : dividers) {
            result.addAll(array.stream().filter(x -> isDividedBy(x, divider)).collect(Collectors.toSet()));
        }
        return result;
    }

    static Set<Integer> filterNotDividedBy(Collection<Integer> array, Integer... dividers) {
        Set<Integer> result = new HashSet<>();
        for (int divider : dividers) {
            result.addAll(array.stream().filter(x -> !isDividedBy(x, divider)).collect(Collectors.toSet()));
        }
        return result;
    }

    static List<Integer> filterSimpleNumbers(Collection<Integer> array) {
        return array.stream().filter(Matrix::isSimpleNumber).collect(Collectors.toList());
    }

    static List<Integer> filterLuckyNumbers(Collection<Integer> array) {
        return array.stream().filter(Matrix::isLuckyNumber).collect(Collectors.toList());
    }

    private static boolean isLuckyNumber(int n) {
        final int luckyNumberLength = 6;
        final int edge = luckyNumberLength / 2;

        String number = String.valueOf(n);
        if (number.length() != luckyNumberLength) {
            return false;
        }
        List<Integer> numbers = Arrays.stream(number.split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return sum(numbers.subList(0, edge)) == sum(numbers.subList(edge, luckyNumberLength));
    }

    private static int sum(List<Integer> numbers) {
        int s = 0;
        for (int number : numbers) {
            s += number;
        }
        return s;
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1 - task 1");
            System.out.println("2 - task 2");
            System.out.println("3 - task 3");
            System.out.println("4 - task 4");
            System.out.println("5 - task 5");
            System.out.println("6 - task 6");
            System.out.println("7 - task 7");
            System.out.println("other - exit\n\nTask: ");
            switch (in.nextLine().trim()) {
                case "1": {
                   task1_OddEvenNumbers();
                    break;
                }
                case "2": {
                    task2_DividedBy3Or9();
                    break;
                }
                case "3": {
                    task3_DividedBy5Or10();
                    break;
                }
                case "4": {
                    task4_GcdLcmForArray();
                    break;
                }
                case "5": {
                    task5_SimpleNumbers();
                    break;
                }
                case "6": {
                    task6_LuckyNumbers();
                    break;
                }
                case "7": {
                    task7_NumberName();
                    break;
                }
                default: {
                    return;
                }
            }
        }
    }

}
