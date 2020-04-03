package com.pack.daoimpl;

import com.pack.dao.AccountDao;
import com.pack.dao.OperationDao;
import com.pack.database.JdbcService;
import com.pack.exceptions.DatabaseException;
import com.pack.exceptions.ServiceError;
import com.pack.model.Account;
import com.pack.model.Operation;
import com.pack.model.OperationType;
import com.pack.utils.ConverterUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pack.exceptions.ServiceError.NOT_ENOUGH_MONEY;

public class OperationDaoImpl implements OperationDao {

    private AccountDao accountDao = new AccountDaoImpl();

    @Override
    public void updateAccountAmount(UUID token, Operation operation) throws SQLException, DatabaseException {
        List<UUID> accounts = accountDao.getUserAccounts(token).stream()
                .map(Account::getId)
                .collect(Collectors.toList());
        if (-1 == accounts.indexOf(operation.getAccountTo())) {
            throw new DatabaseException(ServiceError.ACCOUNT_NOT_FOUND);
        }
        JdbcService.updateAccountAmount(operation.getAccountTo(), operation.getAmountAfter());
        JdbcService.insertOperation(operation);
    }

    @Override
    public void transferMoneyToAccount(Operation operation, UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(ServiceError.USER_NOT_AUTHORIZED);
        }

        Account accountFrom = accountDao.getAccountById(operation.getAccountFrom(), token);
        Account accountTo = accountDao.getAccountById(operation.getAccountTo(), token);
        BigDecimal amount = ConverterUtils.convert(operation.getAmount(),
                operation.getAccCode(), accountFrom.getAccCode());

        BigDecimal accountAmount = accountFrom.getAmount();
        if (accountAmount.compareTo(amount) < 0) {
            throw new IllegalArgumentException(NOT_ENOUGH_MONEY.message());
        }
        operation.setAmountBefore(accountAmount);
        operation.setAmountAfter(accountAmount.subtract(amount));

        BigDecimal recipientAmountBefore = accountTo.getAmount();
        BigDecimal recipientAmountAfter = recipientAmountBefore.add(ConverterUtils.convert(amount,
                accountFrom.getAccCode(), accountTo.getAccCode()));

        JdbcService.updateAccountAmount(operation.getAccountFrom(), operation.getAmountAfter());
        JdbcService.updateAccountAmount(operation.getAccountTo(), recipientAmountAfter);
        JdbcService.insertOperation(operation);

        operation.setAmountBefore(recipientAmountBefore);
        operation.setAmountAfter(recipientAmountAfter);
        operation.setType(OperationType.RECEIPTS);
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
