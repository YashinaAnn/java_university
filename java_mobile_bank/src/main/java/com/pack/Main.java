package com.pack;

import com.pack.dao.AccountDao;
import com.pack.daoimpl.AccountDaoImpl;
import com.pack.daoimpl.OperationDaoImpl;
import com.pack.daoimpl.UserDaoImpl;
import com.pack.dto.*;
import com.pack.model.Operation;
import com.pack.services.AccountService;
import com.pack.services.OperationsService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static AccountDao accountDao = new AccountDaoImpl();
    private static AccountService accountService = new AccountService(new UserDaoImpl(), accountDao);
    private static OperationsService operationsService = new OperationsService(new OperationDaoImpl(), accountDao);

    public static void main(String[] args) {
        while (true) {
            System.out.print("Chose operation:\n"
                    + "1 - create user\n2 - login user by login\n3 - login user by phone\n"
                    + "another - exit\n>>");
            String code = in.next();
            switch (code) {
                case "1": {
                    UserDto user = readUser();
                    System.out.println(accountService.createUser(user));
                    break;
                } 
                case "2": case "3": {
                    boolean byLogin = "2".equals(code);
                    CredentialsDto credentials = byLogin ? readCredentialsLogin() : readCredentialsPhone();
                    Response<UUID> response = byLogin ?
                            accountService.loginUserByLogin(credentials) :
                            accountService.loginUserByPhone(credentials);
                    String error = response.getError();
                    if (null == error || error.isEmpty()) {
                        authorizedUserOperations(response.getData());
                    } else {
                        System.out.println(error);
                    }
                    break;
                }
                default: {
                    accountService.terminate();
                    return;
                }
            }
        }
    }

    private static void authorizedUserOperations(UUID token) {
        if (null == token) {
            return;
        }
        while (true) {
            System.out.print("Chose operation:\n4 - logout user\n"
                    + "5 - create account\n6 - replenish account\n7 - transfer money to account by phone number\n"
                    + "8 - operations list\n>>");
            String code = in.next();
            switch (code) {
                case  "4": {
                    System.out.println(accountService.logout(token));
                    return;
                }
                case "5": {
                    CreateAccountDto request = readAccountInfo();
                    request.setToken(token);
                    Response<UUID> response = accountService.createAccount(request);
                    String error = response.getError();
                    if (null == error || !error.isEmpty()) {
                        System.out.println(error);
                    }
                    break;
                }
                case "6": {
                    Response<List<UUID>> response = accountService.getUserAccounts(token);
                    String error = response.getError();
                    if (error == null || error.isEmpty()) {
                        List<UUID> accounts = response.getData();
                        UUID userAcc = (accounts.size() == 1) ? accounts.get(0) : choseAccount(accounts);
                        UpdateAccountAmountRequestDto request = readAmountInfo();
                        request.setToken(token);
                        request.setAccountToId(userAcc);
                        System.out.println(operationsService.replenishAccount(request));
                    } else {
                        System.out.println(response.getError());
                        break;
                    }
                    break;
                }
                case "7": {
                    Response<List<UUID>> response = accountService.getUserAccounts(token);
                    UpdateAccountAmountRequestDto request;
                    UUID userAccount;
                    String error = response.getError();
                    if (error == null || error.isEmpty()) {
                        List<UUID> userAccounts = response.getData();
                        userAccount = (userAccounts.size() == 1) ? userAccounts.get(0) : choseAccount(userAccounts);
                    } else {
                        System.out.println(response.getError());
                        break;
                    }
                    String phone = readPhone();
                    response = accountService.getUserAccountsByPhone(token, phone);
                    error = response.getError();
                    if (error == null || error.isEmpty()) {
                        List<UUID> accounts = response.getData();
                        UUID toAcc = (accounts.size() == 1) ? accounts.get(0) : choseAccount(accounts);

                        request = readAmountInfo();
                        request.setAccountFromId(userAccount);
                        request.setAccountToId(toAcc);
                    } else {
                        System.out.println(response.getError());
                        break;
                    }
                    request.setToken(token);
                    System.out.println(operationsService.transferMoneyToAccount(request));
                    break;
                }
                case "8": {
                    Response<List<Operation>> response = operationsService.getOperationsList(token);
                    String error = response.getError();
                    if (null == error || error.isEmpty()) {
                        System.out.println("---------------------------------");
                        System.out.println("OPERATIONS LIST:");
                        System.out.println("---------------------------------");
                        response.getData().forEach(s -> System.out.println(s));
                        System.out.println("---------------------------------\n\n");

                    } else {
                        System.out.println(error);
                    }
                    break;
                }
            }
        }
    }

    private static String readPhone() {
        System.out.print("Enter phone: ");
        return in.next();
    }

    private static CredentialsDto readCredentialsLogin() {
        System.out.print("Enter login: ");
        String login = in.next();
        System.out.print("Enter password: ");
        String password = in.next();
        return new CredentialsDto(login, password);
    }

    private static CredentialsDto readCredentialsPhone() {
        String phone = readPhone();
        System.out.print("Enter password: ");
        String password = in.next();
        return new CredentialsDto(phone, password);
    }

    private static UserDto readUser() {
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

    private static CreateAccountDto readAccountInfo() {
        System.out.print("Enter account code: ");
        String accCode = in.next();
        return new CreateAccountDto(accCode);
    }

    private static UpdateAccountAmountRequestDto readAmountInfo() {
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
        System.out.print("Enter account code: ");
        String accCode = in.next();
        return new UpdateAccountAmountRequestDto(amount, accCode);
    }

    private static UUID choseAccount(List<UUID> accounts){
        List<String> accountsStr = accounts.stream()
                .map(acc -> acc.toString()).collect(Collectors.toList());
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
