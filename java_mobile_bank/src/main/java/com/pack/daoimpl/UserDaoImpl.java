package com.pack.daoimpl;

import com.pack.dao.UserDao;
import com.pack.database.ServiceError;
import com.pack.database.DatabaseException;
import com.pack.database.JdbcService;
import com.pack.model.User;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {


    public void createUser(User user) throws DatabaseException, SQLException {
        if (null != JdbcService.getUserByLogin(user.getLogin())){
            throw new DatabaseException(ServiceError.LOGIN_EXISTS);
        }
        if (null != JdbcService.getUserByPhone(user.getPhone())) {
            throw new DatabaseException(ServiceError.PHONE_EXISTS);
        }
        JdbcService.insertUser(user);
    }
}
