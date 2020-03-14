package com.pack.services;

import com.pack.database.DatabaseException;
import com.pack.database.ServiceError;
import com.pack.dto.CreateAccountDto;
import com.pack.dto.CredentialsDto;
import com.pack.dto.Response;
import com.pack.dto.UserDto;
import org.junit.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static com.pack.model.AccCode.RUB;
import static com.pack.model.AccCode.USD;
import static org.junit.Assert.assertEquals;

public class AccountServiceTest extends BaseTest {

    private UserDto user = new UserDto("user", "test", "1234567890", "Street 2");

    @Test
    public void testLoginUserByLoginSuccessful() {
        assertEquals(accountService.createUser(user), "User is created!");
        Response<UUID> response = accountService.loginUserByLogin(new CredentialsDto(user.getLogin(), user.getPassword()));
        assertEquals(response.getError(), "");
    }

    @Test
    public void testLoginUserByPhoneSuccessful() {
        assertEquals(accountService.createUser(user), "User is created!");
        Response<UUID> response = accountService.loginUserByPhone(new CredentialsDto(user.getPhone(), user.getPassword()));
        assertEquals(response.getError(), "");
    }

    @Test
    public void testLoginUserByLoginWrongLogin() {
        assertEquals(accountService.createUser(user), "User is created!");
        Response<UUID> response = accountService.loginUserByLogin(new CredentialsDto("unknown", user.getPassword()));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.getMessage());
    }

    @Test
    public void testLoginUserByLoginWrongPassword() {
        assertEquals(accountService.createUser(user), "User is created!");
        Response<UUID> response = accountService.loginUserByLogin(new CredentialsDto(user.getLogin(), "unknown"));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.getMessage());
    }

    @Test
    public void testLoginUserByPhoneWrongPhone() {
        assertEquals(accountService.createUser(user), "User is created!");
        Response<UUID> response = accountService.loginUserByPhone(new CredentialsDto("1111111111", user.getPassword()));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.getMessage());
    }

    @Test
    public void testLoginUserByPhoneWrongPassword() {
        assertEquals(accountService.createUser(user), "User is created!");
        Response<UUID> response = accountService.loginUserByPhone(new CredentialsDto(user.getPhone(), "unknown"));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.getMessage());
    }

    @Test
    public void testCreateAccountSuccessful() throws SQLException, DatabaseException {
        UUID token = createAndLoginUser(user);

        Response<UUID> response = accountService.createAccount(new CreateAccountDto(token, RUB.toString()));
        assertEquals("", response.getError());

        List<UUID> accounts = accountDao.getUserAccounts(token);
        assertEquals(1, accounts.size());
        assertEquals(accounts.get(0), response.getData());
    }

    @Test
    public void testCreateAccountNotAuthorized() {
        UUID token = createAndLoginUser(user);

        accountService.logout(token);
        Response<UUID> response = accountService.createAccount(new CreateAccountDto(token, RUB.toString()));
        assertEquals(response.getError(), ServiceError.USER_NOT_AUTHORIZED.getMessage());
    }

    @Test
    public void testGetAccountsByPhone() throws SQLException, DatabaseException {
        UUID token = createAndLoginUser(user);

        Response<UUID> response = accountService.createAccount(new CreateAccountDto(token, RUB.toString()));
        assertEquals(response.getError(), "");
        UUID account1 = response.getData();

        response = accountService.createAccount(new CreateAccountDto(token, USD.toString()));
        assertEquals(response.getError(), "");
        UUID account2 = response.getData();

        List<UUID> accounts = accountDao.getUserAccounts(token, user.getPhone());
        assertEquals(accounts.size(), 2);
        Assert.assertTrue(accounts.indexOf(account1) > -1);
        Assert.assertTrue(accounts.indexOf(account2) > -1);
    }

    @Test
    public void testCreateUserExistedLogin() {
        createUser(user);
        user.setPhone("1111111111");
        assertEquals(accountService.createUser(user), ServiceError.LOGIN_EXISTS.getMessage());
    }

    @Test
    public void testCreateUserExistedPhone() {
        createUser(user);
        user.setLogin("new-user");
        assertEquals(accountService.createUser(user), ServiceError.PHONE_EXISTS.getMessage());
    }
}
