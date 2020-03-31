package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.dao.OperationDao;
import com.pack.dto.Response;
import com.pack.dto.TransferMoneyRequestDto;
import com.pack.exceptions.DatabaseException;
import com.pack.model.Account;
import com.pack.model.Operation;
import com.pack.model.OperationType;
import com.pack.utils.ConverterUtils;
import com.pack.utils.Utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class OperationsService {

    private OperationDao operationDao;
    private AccountDao accountDao;

    public OperationsService(OperationDao operationDao, AccountDao accountDao) {
        this.accountDao = accountDao;
        this.operationDao = operationDao;
    }

    public String replenishAccount(TransferMoneyRequestDto request) {
        try {
            Account.validateAmount(request.getAmount());
            Operation operation = new Operation(request);
            operation.setType(OperationType.REPLENISHMENT);
            Account account = accountDao
                    .getAccountById(request.getAccountToId(), request.getToken());
            BigDecimal amount = ConverterUtils.convert(operation.getAmount(),
                    operation.getAccCode(), account.getAccCode());
            BigDecimal amountBefore = account.getAmount();
            BigDecimal amountAfter = amountBefore.add(amount);
            operation.setAmountBefore(amountBefore);
            operation.setAmountAfter(amountAfter);

            operationDao.updateAccountAmount(request.getToken(), operation);
            return "";
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return e.getMessage();
        }
    }

    public String transferMoneyToAccount(TransferMoneyRequestDto request) {
        try {
            Account.validateAmount(request.getAmount());
            operationDao.transferMoneyToAccount(new Operation(request), request.getToken());
            return "";
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return e.getMessage();
        }
    }

    public Response<List<Operation>> getOperationsList(UUID token) {
        try {
            Utils.validateToken(token);
            List<Operation> operations = operationDao.getOperationsList(token);
            return new Response<>(operations, "");
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return new Response<>(null, e.getMessage());
        }
    }
}
