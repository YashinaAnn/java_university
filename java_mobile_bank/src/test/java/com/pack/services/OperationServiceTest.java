package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.dao.OperationDao;
import com.pack.daoimpl.AccountDaoImpl;
import com.pack.daoimpl.OperationDaoImpl;
import com.pack.database.DatabaseException;
import com.pack.database.ServiceError;
import com.pack.dto.*;
import com.pack.model.AccCode;
import com.pack.model.Account;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

import static com.pack.model.AccCode.RUB;
import static com.pack.model.AccCode.USD;
import static com.pack.utils.Constants.DELTA;
import static com.pack.utils.Constants.USD_TO_RUB;
import static org.junit.Assert.assertEquals;

public class OperationServiceTest extends BaseTest {

    OperationDao operationDao = new OperationDaoImpl();
    AccountDao accountDao = new AccountDaoImpl();
    OperationsService operationsService = new OperationsService(operationDao, accountDao);

    UserDto user = new UserDto("user1", "test", "1234567890", "Street 2");
    UserDto userRecipient = new UserDto("user2", "test", "1234567899", "Street 3");

    @Test
    public void testReplenishAccountSameAccCodeSuccessful() throws SQLException, DatabaseException {
        UUID token = createAndLoginUser(user);
        UUID accountId = createAccount(token);

        Account account = accountDao.getAccountById(accountId, token);
        assertEquals(account.getAmount().doubleValue(), 0, DELTA);

        assertEquals(operationsService.replenishAccount(new UpdateAccountAmountRequestDto(token, accountId,
                new BigDecimal(100), RUB.toString())), "");

        account = accountDao.getAccountById(accountId, token);
        assertEquals(account.getAmount().doubleValue(), 100, DELTA);
    }

    @Test
    public void testReplenishAccountDifferentAccCodeSuccessful() throws SQLException, DatabaseException {
        UUID token = createAndLoginUser(user);
        UUID accountId = createAccount(token);

        Account account = accountDao.getAccountById(accountId, token);
        assertEquals(0, account.getAmount().doubleValue(), DELTA);

        assertEquals("", operationsService.replenishAccount(new UpdateAccountAmountRequestDto(token, accountId,
                new BigDecimal(100), AccCode.USD.toString())));

        account = accountDao.getAccountById(accountId, token);
        assertEquals(6691, account.getAmount().doubleValue(), DELTA);
    }

    @Test
    public void testTransportMoneySameAccCodeSuccessful() throws SQLException, DatabaseException{
        UUID token = createAndLoginUser(user);
        UUID accountId = createAccount(token);

        UUID tokenRecipient = createAndLoginUser(userRecipient);
        UUID accountIdRecipient = createAccount(tokenRecipient);

        Account account = accountDao.getAccountById(accountId, token);
        assertEquals(account.getAmount().doubleValue(), 0, DELTA);

        Account accountRecipient = accountDao.getAccountById(accountIdRecipient, token);
        assertEquals(accountRecipient.getAmount().doubleValue(), 0, DELTA);

        assertEquals(operationsService.replenishAccount(new UpdateAccountAmountRequestDto(token, accountId,
                new BigDecimal(100), RUB.toString())), "");

        account = accountDao.getAccountById(accountId, token);
        assertEquals(100, account.getAmount().doubleValue(), DELTA);

        operationsService.transferMoneyToAccount(new UpdateAccountAmountRequestDto(
                token, accountIdRecipient, accountId, new BigDecimal(50), RUB.toString()));

        assertEquals(50, accountDao.getAccountById(accountId, token).getAmount().doubleValue(), DELTA);
        assertEquals(50, accountDao.getAccountById(accountIdRecipient, token).getAmount().doubleValue(), DELTA);
    }

    @Test
    public void testTransportMoneyDifferentAccCodeSuccessful() throws SQLException, DatabaseException{
        UUID token = createAndLoginUser(user);
        UUID accountId = createAccount(token, USD.toString());

        UUID tokenRecipient = createAndLoginUser(userRecipient);
        UUID accountIdRecipient = createAccount(tokenRecipient);

        Account account = accountDao.getAccountById(accountId, token);
        assertEquals(account.getAmount().doubleValue(), 0, DELTA);

        Account accountRecipient = accountDao.getAccountById(accountIdRecipient, token);
        assertEquals(accountRecipient.getAmount().doubleValue(), 0, DELTA);

        assertEquals(operationsService.replenishAccount(new UpdateAccountAmountRequestDto(token, accountId,
                new BigDecimal(150), USD.toString())), "");
        account = accountDao.getAccountById(accountId, token);
        assertEquals(150, account.getAmount().doubleValue(), DELTA);

        assertEquals("", operationsService.replenishAccount(
                new UpdateAccountAmountRequestDto(tokenRecipient, accountIdRecipient,
                new BigDecimal(1000), RUB.toString())));
        account = accountDao.getAccountById(accountIdRecipient, token);
        assertEquals(1000, account.getAmount().doubleValue(), DELTA);

        operationsService.transferMoneyToAccount(new UpdateAccountAmountRequestDto(
                token, accountIdRecipient, accountId, new BigDecimal(50), USD.toString()));

        assertEquals(100, accountDao.getAccountById(accountId, token).getAmount().doubleValue(), DELTA);
        assertEquals(50 * USD_TO_RUB + 1000,
                accountDao.getAccountById(accountIdRecipient, token).getAmount().doubleValue(), DELTA);
    }

    @Test
    public void testTransportMoneyNotEnoughMoney() throws SQLException, DatabaseException{
        UUID token = createAndLoginUser(user);
        UUID accountId = createAccount(token);

        UUID tokenRecipient = createAndLoginUser(userRecipient);
        UUID accountIdRecipient = createAccount(tokenRecipient);

        assertEquals(ServiceError.NOT_ENOUGH_MONEY.getMessage(),
                operationsService.transferMoneyToAccount(new UpdateAccountAmountRequestDto(
                token, accountIdRecipient, accountId, new BigDecimal(50), RUB.toString())));

        assertEquals(0, accountDao.getAccountById(accountId, token).getAmount().doubleValue(), DELTA);
        assertEquals(0, accountDao.getAccountById(accountIdRecipient, token).getAmount().doubleValue(), DELTA);
    }
}
