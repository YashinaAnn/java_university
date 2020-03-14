package com.pack.daoimpl;

import com.pack.dao.AccountDao;
import com.pack.database.ServiceError;
import com.pack.database.DatabaseException;
import com.pack.database.JdbcService;
import com.pack.model.Account;
import com.pack.model.AuthorizedUser;
import com.pack.model.Credentials;
import com.pack.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.pack.database.ServiceError.UNKNOWN_USER;
import static com.pack.database.ServiceError.USER_NOT_AUTHORIZED;

public class AccountDaoImpl implements AccountDao {

    public Account getAccountById(UUID accountId, UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        Account account = JdbcService.getAccount(accountId);
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

    public void logout(UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        JdbcService.deleteAuthorizedUser(token);
    }

    public UUID loginByPhone(Credentials credentials) throws SQLException, DatabaseException {
        return login(JdbcService.getUserByPhone(credentials.getLogin()), credentials);
    }

    public UUID loginByLogin(Credentials credentials) throws DatabaseException, SQLException {
        return login(JdbcService.getUserByLogin(credentials.getLogin()), credentials);
    }

    public UUID login(User user, Credentials credentials) throws DatabaseException, SQLException {
        if (null == user || !user.getPassword().equals(credentials.getPassword())) {
            throw new DatabaseException(UNKNOWN_USER);
        }
        String userToken = JdbcService.getAuthorizedUserById(user.getId());
        if (null != userToken) {
            return UUID.fromString(userToken);
        }
        UUID token = UUID.randomUUID();
        JdbcService.insertAuthorizedUser(new AuthorizedUser(token, user.getId()));
        return token;
    }

    @Override
    public List<UUID> getUserAccounts(UUID token) throws SQLException, DatabaseException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        return JdbcService.getUserAccounts(userId);
    }

    @Override
    public List<UUID> getUserAccounts(UUID token, String phone) throws SQLException, DatabaseException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        User user = JdbcService.getUserByPhone(phone);
        if (null == user) {
            throw new DatabaseException(UNKNOWN_USER);
        }
        return JdbcService.getUserAccounts(user.getId());
    }
}
