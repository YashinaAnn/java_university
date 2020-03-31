package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.dao.UserDao;
import com.pack.daoimpl.AccountDaoImpl;
import com.pack.daoimpl.UserDaoImpl;
import com.pack.dto.CreateAccountRequestDto;
import com.pack.dto.Response;
import com.pack.dto.UserDto;
import com.pack.exceptions.DatabaseException;
import com.pack.exceptions.ServiceError;
import com.pack.model.Account;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pack.model.Currency.RUB;
import static com.pack.model.Currency.USD;
import static org.junit.Assert.assertEquals;

public class AccountServiceTest extends BaseTest {

    private UserDto user = new UserDto("user", "test", "1234567890", "Street 2");
    private UserDao userDao = new UserDaoImpl();
    private UserService userService = new UserService(userDao);
    private AccountDao accountDao = new AccountDaoImpl();
    private AccountService accountService = new AccountService(accountDao);

    @Test
    public void testCreateAccountSuccessful() throws SQLException, DatabaseException {
        UUID token = createAndLoginUser(user);

        Response<UUID> response = accountService.createAccount(new CreateAccountRequestDto(token, RUB.toString()));
        assertEquals("", response.getError());

        List<Account> accounts = accountDao.getUserAccounts(token);
        assertEquals(1, accounts.size());
        assertEquals(accounts.get(0).getId(), response.getData());
    }

    @Test
    public void testCreateAccountNotAuthorized() {
        UUID token = createAndLoginUser(user);

        userService.logout(token);
        Response<UUID> response = accountService.createAccount(new CreateAccountRequestDto(token, RUB.toString()));
        assertEquals(response.getError(), ServiceError.USER_NOT_AUTHORIZED.message());
    }

    @Test
    public void testGetAccountsByPhone() throws SQLException, DatabaseException {
        UUID token = createAndLoginUser(user);

        Response<UUID> response = accountService.createAccount(new CreateAccountRequestDto(token, RUB.toString()));
        assertEquals(response.getError(), "");
        UUID account1 = response.getData();

        response = accountService.createAccount(new CreateAccountRequestDto(token, USD.toString()));
        assertEquals(response.getError(), "");
        UUID account2 = response.getData();

        List<UUID> accounts = accountDao.getUserAccounts(token, user.getPhone()).stream()
                .map(Account::getId).collect(Collectors.toList());
        assertEquals(accounts.size(), 2);
        Assert.assertTrue(accounts.indexOf(account1) > -1);
        Assert.assertTrue(accounts.indexOf(account2) > -1);
    }

    @Test
    public void testCreateUserExistedLogin() {
        createUser(user);
        user.setPhone("1111111111");
        assertEquals(userService.createUser(user), ServiceError.LOGIN_EXISTS.message());
    }

    @Test
    public void testCreateUserExistedPhone() {
        createUser(user);
        user.setLogin("new-user");
        assertEquals(userService.createUser(user), ServiceError.PHONE_EXISTS.message());
    }
}
