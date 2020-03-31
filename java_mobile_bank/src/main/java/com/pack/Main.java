package com.pack;

import com.pack.dao.AccountDao;
import com.pack.daoimpl.AccountDaoImpl;
import com.pack.daoimpl.OperationDaoImpl;
import com.pack.daoimpl.UserDaoImpl;
import com.pack.dto.*;
import com.pack.model.Account;
import com.pack.model.Operation;
import com.pack.services.AccountService;
import com.pack.services.OperationsService;
import com.pack.services.UserService;
import java.util.*;
import java.util.stream.Collectors;

import static com.pack.client.ClientDataReader.*;

public class Main {

    private static Scanner in = new Scanner(System.in);
    private static AccountDao accountDao = new AccountDaoImpl();
    private static AccountService accountService = new AccountService(accountDao);
    private static UserService userService = new UserService(new UserDaoImpl());
    private static OperationsService operationsService = new OperationsService(new OperationDaoImpl(), accountDao);

    public static void main(String[] args) {
        while (true) {
            System.out.print("Chose operation:\n"
                    + "1 - sign up\n2 - login by login\n3 - login by phone\n"
                    + "another - exit\n>>");
            String code = in.next();
            switch (code) {
                case "1": {
                    UserDto user = readUser();
                    System.out.println(userService.createUser(user));
                    break;
                }
                case "2": case "3": {
                    boolean byLogin = "2".equals(code);
                    CredentialsDto credentials = byLogin ? readCredentialsLogin() : readCredentialsPhone();
                    Response<UUID> response = byLogin ?
                            userService.loginUserByLogin(credentials) :
                            userService.loginUserByPhone(credentials);
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
                    + "8 - operations list\n9 - accounts info\n>> ");
            String code = in.next();
            switch (code) {
                case  "4": {
                    System.out.println(userService.logout(token));
                    return;
                }
                case "5": {
                    CreateAccountRequestDto request = readAccountInfo();
                    request.setToken(token);
                    Response<UUID> response = accountService.createAccount(request);
                    String error = response.getError();
                    if (null == error || !error.isEmpty()) {
                        System.out.println(error);
                    }
                    break;
                }
                case "6": {
                    Response<List<Account>> response = accountService.getUserAccounts(token);
                    String error = response.getError();
                    if (error == null || error.isEmpty()) {
                        List<UUID> accounts = response.getData().stream()
                                .map(Account::getId)
                                .collect(Collectors.toList());
                        UUID userAcc = (accounts.size() == 1) ? accounts.get(0) : choseAccount(accounts);
                        TransferMoneyRequestDto request = readAmountInfo();
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
                    Response<List<Account>> response = accountService.getUserAccounts(token);
                    TransferMoneyRequestDto request;
                    UUID userAccount;
                    String error = response.getError();
                    if (error == null || error.isEmpty()) {
                        List<UUID> userAccounts = response.getData().stream()
                                .map(Account::getId)
                                .collect(Collectors.toList());
                        userAccount = (userAccounts.size() == 1) ? userAccounts.get(0) : choseAccount(userAccounts);
                    } else {
                        System.out.println(response.getError());
                        break;
                    }

                    String phone = readPhone();
                    response = accountService.getUserAccountsByPhone(token, phone);
                    error = response.getError();
                    if (error == null || error.isEmpty()) {
                        List<UUID> accounts = response.getData().stream()
                                .map(Account::getId)
                                .collect(Collectors.toList());
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
                        response.getData().forEach(System.out::println);
                        System.out.println("---------------------------------\n\n");

                    } else {
                        System.out.println(error);
                    }
                    break;
                }
                case "9": {
                    Response<List<Account>> response = accountService.getUserAccounts(token);
                    String error = response.getError();
                    if (null == error || error.isEmpty()) {
                        response.getData().forEach(System.out::println);
                    } else {
                        System.out.println();
                    }
                    break;
                }
            }
        }
    }
}
