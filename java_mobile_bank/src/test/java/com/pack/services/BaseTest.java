package com.pack.services;

import com.pack.dao.AccountDao;
import com.pack.dao.UserDao;
import com.pack.daoimpl.AccountDaoImpl;
import com.pack.daoimpl.UserDaoImpl;
import com.pack.database.JdbcService;
import com.pack.database.JdbcUtils;
import com.pack.dto.CreateAccountRequestDto;
import com.pack.dto.CredentialsDto;
import com.pack.dto.Response;
import com.pack.dto.UserDto;
import com.pack.model.Currency;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BaseTest {

    private static boolean setUpIsDone = false;
    private static AccountDao accountDao = new AccountDaoImpl();
    private static UserDao userDao = new UserDaoImpl();
    private static AccountService accountService = new AccountService(accountDao);
    private static UserService userService = new UserService(userDao);

    @BeforeClass()
    public static void setUp() {
        if (!setUpIsDone) {
            boolean createConnection = JdbcUtils.createConnection();
            if(!createConnection) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }
    }

    @AfterClass
    public static void close() {
        if (setUpIsDone)
            JdbcUtils.closeConnection();
    }

    @Before()
    public void clearDatabase() throws SQLException {
        JdbcService.deleteOperations();
        JdbcService.deleteUsers();
    }

    void createUser(UserDto user) {
        Assert.assertEquals(userService.createUser(user), "User is created!");
    }

    UUID createAndLoginUser(UserDto user) {
        createUser(user);
        Response<UUID> response = userService.loginUserByLogin(
                new CredentialsDto(user.getLogin(), user.getPassword()));
        assertEquals("", response.getError());
        return response.getData();
    }

    UUID createAccount(UUID token) {
        return createAccount(token, Currency.RUB.toString());
    }

    UUID createAccount(UUID token, String accCode) {
        Response<UUID> response = accountService.createAccount(new CreateAccountRequestDto(token, accCode));
        assertEquals("", response.getError());
        return response.getData();
    }
}
