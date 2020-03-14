package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.dao.OperationDao;
import com.pack.database.DatabaseException;
import com.pack.dto.Response;
import com.pack.dto.UpdateAccountAmountRequestDto;
import com.pack.model.Account;
import com.pack.model.Operation;
import com.pack.model.UpdateAccountAmountInfo;
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

    public String replenishAccount(UpdateAccountAmountRequestDto request) {
        try {
            UpdateAccountAmountInfo info = new UpdateAccountAmountInfo(request);
            Account account = accountDao.getAccountById(request.getAccountToId(), info.getToken());
            BigDecimal amount = ConverterUtils.convert(info.getAmount(), info.getAccCode(), account.getAccCode());
            operationDao.updateAccountAmount(info.getToken(), info.getAccountToId(), account.getAmount().add(amount));
            return "";
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return e.getMessage();
        }
    }

    public String transferMoneyToAccount(UpdateAccountAmountRequestDto request) {
        try {
            UpdateAccountAmountInfo info = new UpdateAccountAmountInfo(request);
            operationDao.transferMoneyToAccount(new Operation(request), info.getToken());
            return "";
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return e.getMessage();
        }
    }

    public Response<List<Operation>> getOperationsList(UUID token) {
        try {
            Utils.validateToken(token);
            List<Operation> operations = operationDao.getOperationsList(token);
            return new Response<>(operations, "");
        } catch (IllegalArgumentException | DatabaseException | SQLException e) {
            return new Response<>(null, e.getMessage());
        }
    }
}
