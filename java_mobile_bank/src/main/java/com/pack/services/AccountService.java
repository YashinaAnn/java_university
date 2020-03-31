package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.database.JdbcUtils;
import com.pack.dto.CreateAccountRequestDto;
import com.pack.dto.Response;
import com.pack.exceptions.DatabaseException;
import com.pack.exceptions.ServiceError;
import com.pack.model.Account;
import com.pack.model.User;
import com.pack.utils.Utils;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class AccountService {

    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Response<UUID> createAccount(CreateAccountRequestDto createAccountDto) {
        try {
            Utils.validateToken(createAccountDto.getToken());
            Account account = new Account(createAccountDto);
            accountDao.createAccount(createAccountDto.getToken(), account);
            return new Response<>(account.getId(), "");
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public Response<List<Account>> getUserAccounts(UUID token) {
        try {
            Utils.validateToken(token);
            List<Account> accounts = accountDao.getUserAccounts(token);
            if (accounts.isEmpty()) {
                throw new IllegalArgumentException(ServiceError.ACCOUNT_NOT_FOUND.message());
            }
            return new Response<>(accounts, "");
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public Response<List<Account>> getUserAccountsByPhone(UUID token, String phone) {
        try {
            Utils.validateToken(token);
            User.validatePhone(phone);
            List<Account> accounts = accountDao.getUserAccounts(token, phone);
            if (accounts.isEmpty()) {
                throw new IllegalArgumentException(ServiceError.ACCOUNT_NOT_FOUND.message());
            }
            return new Response<>(accounts, "");
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public void terminate() {
        JdbcUtils.closeConnection();
    }
}
