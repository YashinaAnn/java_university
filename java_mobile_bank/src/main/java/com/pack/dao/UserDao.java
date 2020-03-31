package com.pack.dao;

import com.pack.exceptions.DatabaseException;
import com.pack.model.Credentials;
import com.pack.model.User;

import java.sql.SQLException;
import java.util.UUID;

public interface UserDao {

    void createUser(User user) throws DatabaseException, SQLException;
    UUID loginByPhone(Credentials credentials) throws SQLException, DatabaseException;
    UUID loginByLogin(Credentials credentials) throws DatabaseException, SQLException;
    UUID login(User user, Credentials credentials) throws DatabaseException, SQLException;
    void logout(UUID login) throws DatabaseException, SQLException;
}
