package com.pack.services;

import com.pack.dao.UserDao;
import com.pack.dto.CredentialsDto;
import com.pack.dto.Response;
import com.pack.dto.UserDto;
import com.pack.exceptions.DatabaseException;
import com.pack.model.Credentials;
import com.pack.model.User;

import java.sql.SQLException;
import java.util.UUID;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public String createUser(UserDto userDto) {
        try {
            User.validatePhone(userDto.getPhone());
            User user = new User(userDto);
            userDao.createUser(user);
            return "User is created!";
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return e.getMessage();
        }
    }

    public Response<UUID> loginUserByPhone(CredentialsDto credentialsDto) {
        try {
            Credentials credentials = new Credentials(credentialsDto);
            User.validatePhone(credentials.getLogin());
            return new Response<>(userDao.loginByPhone(credentials), "");
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public Response<UUID> loginUserByLogin(CredentialsDto credentialsDto) {
        try {
            Credentials credentials = new Credentials(credentialsDto);
            return new Response<>(userDao.loginByLogin(credentials), "");
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return new Response<>(null, e.getMessage());
        }
    }

    public String logout(UUID token) {
        try {
            userDao.logout(token);
            return "";
        } catch (IllegalArgumentException | DatabaseException | SQLException | NullPointerException e) {
            return e.getMessage();
        }
    }
}
