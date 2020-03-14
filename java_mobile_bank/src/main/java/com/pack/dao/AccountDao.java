package com.pack.dao;

import com.pack.database.DatabaseException;
import com.pack.model.Account;
import com.pack.model.Credentials;
import com.pack.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface AccountDao {

    void createAccount(UUID token, Account account) throws DatabaseException, SQLException;
    UUID loginByPhone(Credentials credentials) throws SQLException, DatabaseException;
    UUID loginByLogin(Credentials credentials) throws DatabaseException, SQLException;
    UUID login(User user, Credentials credentials) throws DatabaseException, SQLException;
    void logout(UUID login) throws DatabaseException, SQLException;
    Account getAccountById(UUID accountId, UUID token) throws DatabaseException, SQLException;

    List<UUID> getUserAccounts(UUID token) throws SQLException, DatabaseException;
    List<UUID> getUserAccounts(UUID token, String phone) throws SQLException, DatabaseException;
}
