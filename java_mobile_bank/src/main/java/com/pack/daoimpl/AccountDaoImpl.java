package com.pack.daoimpl;

import com.pack.dao.AccountDao;
import com.pack.database.JdbcService;
import com.pack.exceptions.DatabaseException;
import com.pack.exceptions.ServiceError;
import com.pack.model.Account;
import com.pack.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.pack.exceptions.ServiceError.UNKNOWN_USER;
import static com.pack.exceptions.ServiceError.USER_NOT_AUTHORIZED;


public class AccountDaoImpl implements AccountDao {

    public Account getAccountById(UUID accountId, UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        Account account = JdbcService.getAccountById(accountId);
        if (null == account) {
            throw new DatabaseException(ServiceError.ACCOUNT_NOT_FOUND);
        }
        return account;
    }

    public void createAccount(UUID token, Account account) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        account.setClientId(userId);
        JdbcService.insertAccount(account);
    }

    @Override
    public List<Account> getUserAccounts(UUID token) throws SQLException, DatabaseException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        return JdbcService.getAllUserAccounts(userId);
    }

    @Override
    public List<Account> getUserAccounts(UUID token, String phone) throws SQLException, DatabaseException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        User user = JdbcService.getUserByPhone(phone);
        if (null == user) {
            throw new DatabaseException(UNKNOWN_USER);
        }
        return JdbcService.getAllUserAccounts(user.getId());
    }
}
