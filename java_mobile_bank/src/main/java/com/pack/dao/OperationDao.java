package com.pack.dao;

import com.pack.exceptions.DatabaseException;
import com.pack.model.Operation;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface OperationDao {

    void updateAccountAmount(UUID token, Operation operation) throws SQLException, DatabaseException;
    void transferMoneyToAccount(Operation operation, UUID token) throws DatabaseException, SQLException;
    List<Operation> getOperationsList(UUID token) throws DatabaseException, SQLException;
}
