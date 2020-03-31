package com.pack.services;

import com.pack.daoimpl.UserDaoImpl;
import com.pack.dto.CredentialsDto;
import com.pack.dto.Response;
import com.pack.dto.UserDto;
import com.pack.exceptions.ServiceError;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends BaseTest {

    private UserDto user = new UserDto("user", "test", "1234567890", "Street 2");
    private UserService userService = new UserService(new UserDaoImpl());

    @Test
    public void testLoginUserByLoginSuccessful() {
        assertEquals(userService.createUser(user), "User is created!");
        Response<UUID> response = userService.loginUserByLogin(new CredentialsDto(user.getLogin(), user.getPassword()));
        assertEquals(response.getError(), "");
    }

    @Test
    public void testLoginUserByPhoneSuccessful() {
        assertEquals(userService.createUser(user), "User is created!");
        Response<UUID> response = userService.loginUserByPhone(new CredentialsDto(user.getPhone(), user.getPassword()));
        assertEquals(response.getError(), "");
    }

    @Test
    public void testLoginUserByLoginWrongLogin() {
        assertEquals(userService.createUser(user), "User is created!");
        Response<UUID> response = userService.loginUserByLogin(new CredentialsDto("unknown", user.getPassword()));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.message());
    }

    @Test
    public void testLoginUserByLoginWrongPassword() {
        assertEquals(userService.createUser(user), "User is created!");
        Response<UUID> response = userService.loginUserByLogin(new CredentialsDto(user.getLogin(), "unknown"));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.message());
    }

    @Test
    public void testLoginUserByPhoneWrongPhone() {
        assertEquals(userService.createUser(user), "User is created!");
        Response<UUID> response = userService.loginUserByPhone(new CredentialsDto("1111111111", user.getPassword()));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.message());
    }

    @Test
    public void testLoginUserByPhoneWrongPassword() {
        assertEquals(userService.createUser(user), "User is created!");
        Response<UUID> response = userService.loginUserByPhone(new CredentialsDto(user.getPhone(), "unknown"));
        assertEquals(response.getError(), ServiceError.UNKNOWN_USER.message());
    }
}
