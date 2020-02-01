package com.pack.task4;

import java.util.Scanner;

public class PhoneDictionaryMain {

    private static final Scanner in = new Scanner(System.in);
    private static PhoneDictionary dictionary = PhoneDictionary.getInstance();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1 - add info to dictionary");
            System.out.println("2 - delete info from dictionary");
            System.out.println("3 - update phone");
            System.out.println("4 - update personal info");
            System.out.println("5 - print");
            System.out.println("6 - exit\n");

            switch (in.nextLine().trim()) {
                case "1" : {
                    addInfo();
                    break;
                }
                case "2" : {
                    deleteInfo();
                    break;
                }
                case "3" : {
                    updatePhone();
                    break;
                }
                case "4" : {
                    updateInfo();
                    break;
                }
                case "5" : {
                    dictionary.getPhones().forEach((key, value) -> {
                        System.out.println(key);
                        System.out.println(value);
                    });
                    break;
                }
                case "6": {
                    return;
                }
            }
        }
    }

    public static void addInfo() {
        try {
            Phone phone = readPhone();
            PersonalInfo info = readPersonalInfo();
            dictionary.addPhone(phone, info);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteInfo() {
        try {
            Phone phone = readPhone();
            dictionary.deletePhone(phone);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updatePhone() {
        try {
            System.out.println("Old phone: ");
            Phone oldPhone = readPhone();
            System.out.println("New phone: ");
            Phone newPhone = readPhone();
            dictionary.updatePhone(oldPhone, newPhone);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateInfo() {
        try {
            Phone phone = readPhone();
            PersonalInfo info = readPersonalInfo();
            dictionary.updateInfo(phone, info);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Phone readPhone() throws IllegalArgumentException {
        System.out.print("Enter phone number: ");
        return new Phone(in.nextLine().trim());
    }

    public static PersonalInfo readPersonalInfo() throws IllegalArgumentException {
        System.out.print("Enter first name: ");
        String firstname = in.nextLine();
        System.out.print("Enter last name: ");
        String lastname = in.nextLine();

        System.out.print("Enter street: ");
        String street = in.nextLine();
        System.out.print("Enter house: ");
        String house = in.nextLine();
        System.out.print("Enter city: ");
        String city = in.nextLine();
        System.out.print("Enter country: ");
        String country = in.nextLine();

        Address address = new Address(country, city, street, house);
        return new PersonalInfo(firstname, lastname, address);
    }



}
