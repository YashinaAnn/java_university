package com.pack.dao;

import com.pack.database.DatabaseException;
import com.pack.dto.UpdateAccountAmountRequestDto;
import com.pack.model.Account;
import com.pack.model.Operation;
import com.pack.model.UpdateAccountAmountInfo;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface OperationDao {

    void updateAccountAmount(UUID token, UUID accountId, BigDecimal amount) throws SQLException, DatabaseException;
    void transferMoneyToAccount(Operation operation, UUID token) throws DatabaseException, SQLException;
    List<Operation> getOperationsList(UUID token) throws DatabaseException, SQLException;
}
