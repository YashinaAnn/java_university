package com.pack.client;

import com.pack.dto.CreateAccountRequestDto;
import com.pack.dto.CredentialsDto;
import com.pack.dto.TransferMoneyRequestDto;
import com.pack.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientDataReader {

    private static final Scanner in = new Scanner(System.in);

    public static String readPhone() {
        System.out.print("Enter phone: ");
        return in.next();
    }

    public static CredentialsDto readCredentialsLogin() {
        System.out.print("Enter login: ");
        String login = in.next();
        System.out.print("Enter password: ");
        String password = in.next();
        return new CredentialsDto(login, password);
    }

    public static CredentialsDto readCredentialsPhone() {
        String phone = readPhone();
        System.out.print("Enter password: ");
        String password = in.next();
        return new CredentialsDto(phone, password);
    }

    public static UserDto readUser() {
        System.out.print("Enter login: ");
        String login = in.next();
        System.out.print("Enter password: ");
        String password = in.next();
        System.out.print("Enter phone: ");
        String phone = in.next();
        System.out.print("Enter address: ");
        String address = in.next();
        return new UserDto(login, password, phone, address);
    }

    public static CreateAccountRequestDto readAccountInfo() {
        System.out.print("Enter account code: ");
        String accCode = in.next();
        return new CreateAccountRequestDto(accCode);
    }

    public static TransferMoneyRequestDto readAmountInfo() {
        BigDecimal amount;
        while (true) {
            try {
                System.out.print("Enter amount: ");
                amount = BigDecimal.valueOf(in.nextLong());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Bad value!");
            }
        }
        System.out.print("Enter currency: ");
        String accCode = in.next();
        return new TransferMoneyRequestDto(amount, accCode);
    }

    public static UUID choseAccount(List<UUID> accounts){
        List<String> accountsStr = accounts.stream()
                .map(UUID::toString).collect(Collectors.toList());
        System.out.println("Choose account:");
        accountsStr.forEach(account ->
                System.out.println(String.format("*****%s", account.substring(account.length() - 4))));
        while (true) {
            System.out.print(">>");
            try {
                String chosenAcc = in.next().trim();
                String account = accountsStr.stream()
                        .filter(acc -> acc.substring(acc.length() - 4).equals(chosenAcc))
                        .findFirst().orElseThrow(IllegalArgumentException::new);
                return UUID.fromString(account);
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown account, try again...");
            }
        }
    }
}
