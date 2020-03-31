package com.pack.daoimpl;

import com.pack.dao.UserDao;
import com.pack.database.JdbcService;
import com.pack.exceptions.DatabaseException;
import com.pack.exceptions.ServiceError;
import com.pack.model.AuthorizedUser;
import com.pack.model.Credentials;
import com.pack.model.User;

import java.sql.SQLException;
import java.util.UUID;

import static com.pack.exceptions.ServiceError.UNKNOWN_USER;
import static com.pack.exceptions.ServiceError.USER_NOT_AUTHORIZED;

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

    public void logout(UUID token) throws DatabaseException, SQLException {
        Long userId = JdbcService.getUserIdByToken(token);
        if (null == userId) {
            throw new DatabaseException(USER_NOT_AUTHORIZED);
        }
        JdbcService.deleteAuthorizedUser(token);
    }

    public UUID loginByPhone(Credentials credentials) throws SQLException, DatabaseException {
        return login(JdbcService.getUserByPhone(credentials.getLogin()), credentials);
    }

    public UUID loginByLogin(Credentials credentials) throws DatabaseException, SQLException {
        return login(JdbcService.getUserByLogin(credentials.getLogin()), credentials);
    }

    public UUID login(User user, Credentials credentials) throws DatabaseException, SQLException {
        if (null == user || !user.getPassword().equals(credentials.getPassword())) {
            throw new DatabaseException(UNKNOWN_USER);
        }
        String userToken = JdbcService.getAuthorizedUserById(user.getId());
        if (null != userToken) {
            return UUID.fromString(userToken);
        }
        UUID token = UUID.randomUUID();
        JdbcService.insertAuthorizedUser(new AuthorizedUser(token, user.getId()));
        return token;
    }
}
