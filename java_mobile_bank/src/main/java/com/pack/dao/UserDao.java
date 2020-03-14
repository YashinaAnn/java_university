package com.pack.dao;

import com.pack.database.DatabaseException;
import com.pack.model.User;

import java.sql.SQLException;

public interface UserDao {

    void createUser(User user) throws DatabaseException, SQLException;
}
