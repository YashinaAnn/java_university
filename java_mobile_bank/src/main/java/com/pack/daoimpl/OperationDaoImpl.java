package com.pack.daoimpl;

import com.pack.dao.AccountDao;
import com.pack.dao.OperationDao;
import com.pack.database.ServiceError;
import com.pack.database.DatabaseException;
import com.pack.database.JdbcService;
import com.pack.model.*;
import com.pack.utils.ConverterUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.pack.database.ServiceError.NOT_ENOUGH_MONEY;

public class OperationDaoImpl implements OperationDao {

    private AccountDao accountDao = new AccountDaoImpl();

    public void updateAccountAmount(UUID token, UUID accountId, BigDecimal amount) throws SQLException, DatabaseException {
        List<UUID> accounts = accountDao.getUserAccounts(token);
        if (-1 == accounts.indexOf(accountId)) {
            throw new DatabaseException(ServiceError.ACCOUNT_NOT_FOUND);
        }
        JdbcService.updateAccountAmount(accountId, amount);
    }

    @Override
    public void transferMoneyToAccount(Operation operation, UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(ServiceError.USER_NOT_AUTHORIZED);
        }
        BigDecimal amount = operation.getAmount();
        Account accountFrom = accountDao.getAccountById(operation.getAccountFrom(), token);
        Account accountTo = accountDao.getAccountById(operation.getAccountTo(), token);

        BigDecimal accountAmount = accountFrom.getAmount();
        if (accountAmount.compareTo(amount) < 0) {
            throw new IllegalArgumentException(NOT_ENOUGH_MONEY.getMessage());
        }
        operation.setAmountBefore(accountAmount);
        operation.setAmountAfter(accountAmount.subtract(amount));

        BigDecimal moneyToBefore = accountTo.getAmount();
        BigDecimal moneyToAfter = moneyToBefore.add(ConverterUtils.convert(amount,
                operation.getAccCode(), accountTo.getAccCode()));

        JdbcService.updateAccountAmount(operation.getAccountFrom(), operation.getAmountAfter());
        JdbcService.updateAccountAmount(operation.getAccountTo(), moneyToAfter);
        JdbcService.insertOperation(operation);
    }

    @Override
    public List<Operation> getOperationsList(UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(ServiceError.USER_NOT_AUTHORIZED);
        }
        return JdbcService.getUserOperationsList(userId);
    }
}
