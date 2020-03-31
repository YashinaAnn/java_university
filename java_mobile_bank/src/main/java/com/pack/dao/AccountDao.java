package com.pack.dao;

import com.pack.exceptions.DatabaseException;
import com.pack.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface AccountDao {

    void createAccount(UUID token, Account account) throws DatabaseException, SQLException;
    Account getAccountById(UUID accountId, UUID token) throws DatabaseException, SQLException;
    List<Account> getUserAccounts(UUID token) throws SQLException, DatabaseException;
    List<Account> getUserAccounts(UUID token, String phone) throws SQLException, DatabaseException;
}
