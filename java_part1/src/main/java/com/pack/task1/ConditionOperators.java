package com.pack.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConditionOperators {

    private static final Scanner in = new Scanner(System.in);

    static void task1_SumOfSquare() {
        try {
            System.out.print("Input x: ");
            double x = Double.parseDouble(in.nextLine().trim());
            System.out.print("Input y: ");
            double y = Double.parseDouble(in.nextLine().trim());

            double result = isSumOfSquaresGreater(x, y);
            String message = (result > 0) ? "Sum of squares is greater" :
                    (result < 0) ? "Square of sum is greater" : "Equal!";
            System.out.println(message);
        } catch (NumberFormatException ex) {
            System.out.println("Input data is incorrect");
        }
    }

    static void task2_SalaryIncrease() {
        try {
            System.out.print("Salary: ");
            double salary = Double.parseDouble(in.nextLine().trim());
            System.out.print("Experience: ");
            int experience = Integer.parseInt(in.nextLine().trim());
            double money = getSalaryIncrease(salary, experience);
            System.out.println("Added money: " + money);
            System.out.println("New salary: " + (money + salary));
        } catch (NumberFormatException ex) {
            System.out.println("Input data is incorrect");
        }
    }

    static void task3_DistanceToPoints() {
        try {
            System.out.print("xA = ");
            double xA = Double.parseDouble(in.nextLine().trim());
            System.out.print("yA = ");
            double yA = Double.parseDouble(in.nextLine().trim());

            System.out.print("xB = ");
            double xB = Double.parseDouble(in.nextLine().trim());
            System.out.print("yB = ");
            double yB = Double.parseDouble(in.nextLine().trim());

            double result = isFurther(xA, yA, xB, yB);
            String message = (result > 0) ? "Distance to point A is greater" :
                    (result < 0) ? "Distance to point B is greater" :
                            "Distance to point A equals to distance to point B";
            System.out.println(message);
        } catch (NumberFormatException ex) {
            System.out.println("Bad input, try again");
        }
    }

    static void task4_IsRightTriangle() {
        try {
            System.out.print("a = ");
            double a = Double.parseDouble(in.nextLine().trim());
            System.out.print("b = ");
            double b = Double.parseDouble(in.nextLine().trim());
            System.out.print("c = ");
            double c = Double.parseDouble(in.nextLine().trim());

            String message = isRightTriangle(a, b, c) ? "Triangle is right" : "Triangle is not right";
            System.out.println(message);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void task5_SquareOfPositives() {
        List<Double> numbers = new ArrayList<>();
        try {
            System.out.print("a = ");
            numbers.add(Double.parseDouble(in.nextLine().trim()));
            System.out.print("b = ");
            numbers.add(Double.parseDouble(in.nextLine().trim()));
            System.out.print("c = ");
            numbers.add(Double.parseDouble(in.nextLine().trim()));
            numbers.stream().map(ConditionOperators::getSquareIfPos).forEach(System.out::println);
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input!");
        }
    }

    static void task6_SeasonsByMonth() {
        try {
            System.out.print("Month number: ");
            int monthNum = Integer.parseInt(in.nextLine().trim());
            System.out.println("Season: " + getSeasonByMonth(monthNum));
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    static double isSumOfSquaresGreater(double x, double y) {
        return (x * x + y * y) - (x + y) * (x + y);
    }

    static double getSalaryIncrease(double salary, int experience) {
        int percentage = experience < 2 || experience >= 10 ? 0 : (experience < 5) ? 2 : 5;
        return salary / 100.0 * percentage;
    }

    static double isFurther(double xA, double yA, double xB, double yB) {
        return getDistance(xA, yA) - getDistance(xB, yB);
    }

    private static double getDistance(double x, double y) {
        return x * x + y * y;
    }

    static boolean isRightTriangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0 || (a + b) <= c || (a + c) <= b || (b + c) <= a) {
            throw new IllegalArgumentException("Not a triangle!");
        }
        if (a > c) {
            double temp = a;
            a = c;
            c = temp;
        }
        if (b > c) {
            double temp = b;
            b = c;
            c = temp;
        }
        return (c * c) == (a * a) + (b * b);
    }

    static double getSquareIfPos(double x) {
        return (x < 0) ? x : x * x;
    }

    static String getSeasonByMonth(int month) {
        switch (month) {
            case 12 : case 1 : case 2 : {
                return "Winter";
            }
            case 3 : case 4 : case 5 : {
                return "Spring";
            }
            case 6 : case 7 : case 8 : {
                return "Summer";
            }
            case 9 : case 10 : case 11 : {
                return "Autumn";
            }
            default: {
                throw new IllegalArgumentException("Unknown month!");
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1 - task 1");
            System.out.println("2 - task 2");
            System.out.println("3 - task 3");
            System.out.println("4 - task 4");
            System.out.println("5 - task 5");
            System.out.println("6 - task 6");
            System.out.print("other - exit\n\nTask: ");
            switch (in.nextLine().trim()) {
                case "1": {
                    task1_SumOfSquare();
                    break;
                }
                case "2": {
                    task2_SalaryIncrease();
                    break;
                }
                case "3": {
                    task3_DistanceToPoints();
                    break;
                }
                case "4": {
                    task4_IsRightTriangle();
                    break;
                }
                case "5": {
                    task5_SquareOfPositives();
                    break;
                }
                case "6": {
                    task6_SeasonsByMonth();
                    break;
                }
                default: {
                    return;
                }
            }
        }
    }
}
