package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.dao.UserDao;
import com.pack.database.DatabaseException;
import com.pack.database.JdbcUtils;
import com.pack.dto.*;
import com.pack.model.Account;
import com.pack.model.Credentials;
import com.pack.model.User;
import com.pack.utils.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountService {

    private UserDao userDao;
    private AccountDao accountDao;

    public AccountService(UserDao userDao, AccountDao accountDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    public String createUser(UserDto userDto) {
        try {
            User user = new User(userDto);
            userDao.createUser(user);
            return "User is created!";
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return e.getMessage();
        }
    }

    public Response<UUID> createAccount(CreateAccountDto createAccountDto) {
        try {
            Utils.validateToken(createAccountDto.getToken());
            Account account = new Account(createAccountDto);
            accountDao.createAccount(createAccountDto.getToken(), account);
            return new Response<>(account.getId(), "");
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public Response<UUID> loginUserByPhone(CredentialsDto credentialsDto) {
        try {
            Credentials credentials = new Credentials(credentialsDto);
            User.validatePhone(credentials.getLogin());
            return new Response<>(accountDao.loginByPhone(credentials), "");
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public Response<UUID> loginUserByLogin(CredentialsDto credentialsDto) {
        try {
            Credentials credentials = new Credentials(credentialsDto);
            return new Response<>(accountDao.loginByLogin(credentials), "");
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public String logout(UUID token) {
        try {
            accountDao.logout(token);
            return "";
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return e.getMessage();
        }
    }

    public Response<List<UUID>> getUserAccounts(UUID token) {
        try {
            Utils.validateToken(token);
            List<UUID> accounts = accountDao.getUserAccounts(token);
            if (accounts.isEmpty()) {
                throw new IllegalArgumentException("No created accounts");
            }
            return new Response<>(accounts, "");
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public Response<List<UUID>> getUserAccountsByPhone(UUID token, String phone) {
        try {
            Utils.validateToken(token);
            User.validatePhone(phone);
            List<UUID> accounts = accountDao.getUserAccounts(token, phone);
            if (accounts.isEmpty()) {
                throw new IllegalArgumentException("No created accounts");
            }
            return new Response<>(accounts, "");
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public void terminate() {
        JdbcUtils.closeConnection();
    }
}
