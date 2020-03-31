package com.pack.database;

import com.pack.model.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.pack.utils.Constants.*;
import static com.pack.utils.Constants.OPERATION_TYPE;

public class JdbcService {

    private static final String INSERT_USER = "INSERT INTO users VALUES (null, ?, ?, ?, ?)";
    private static final String INSERT_ACCOUNT = "INSERT INTO accounts VALUES (?, ?, ?, ?)";
    private static final String INSERT_AUTHORIZED_USER = "INSERT INTO authorized_users VALUES (?, ?)";
    private static final String INSERT_OPERATION = "INSERT INTO operations VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_AUTHORIZED_USER = "DELETE FROM authorized_users WHERE token = '%s'";
    private static final String DELETE_USERS = "DELETE FROM users";
    private static final String DELETE_OPERATIONS = "DELETE FROM operations";

    private static final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = '%s'";
    private static final String GET_USER_BY_PHONE = "SELECT * FROM users WHERE phone = '%s'";
    private static final String GET_USER_BY_TOKEN = "SELECT * FROM authorized_users WHERE token = '%s'";
    private static final String GET_AUTHORIZED_USER_BY_ID = "SELECT * FROM authorized_users WHERE id = '%s'";
    private static final String GET_ACCOUNT = "SELECT * FROM accounts WHERE id = '%s'";
    private static final String GET_USER_ACCOUNTS = "SELECT * FROM accounts WHERE client_id = %s";
    private static final String GET_USER_OPERATIONS =
            "SELECT * FROM operations WHERE " +
                    "((account_from IN (SELECT id FROM accounts WHERE client_id = %s) AND operation_type='TRANSFER') " +
                    "OR (account_to IN (SELECT id FROM accounts WHERE client_id = %s) " +
                    "AND (operation_type = 'REPLENISHMENT' OR operation_type = 'RECEIPTS')))";

    private static final String UPDATE_ACCOUNT_AMOUNT = "UPDATE accounts SET amount = ? WHERE id = '%s'";


    public static void insertUser(User user) throws SQLException {
        try (PreparedStatement statement =
                     JdbcUtils.getConnection().prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getPhone());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                user.setId(rs.getInt(1));
            }
            rs.close();
        }
    }

    public static void insertAccount(Account account) throws SQLException {
        try (PreparedStatement statement =
                     JdbcUtils.getConnection().prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getId().toString());
            statement.setLong(2, account.getClientId());
            statement.setBigDecimal(3, account.getAmount());
            statement.setString(4, account.getAccCode().toString());
            statement.executeUpdate();
        }
    }

    public static User getUserByLogin(String login) throws SQLException{
        return getUser(String.format(GET_USER_BY_LOGIN, login));
    }

    public static User getUserByPhone(String phone) throws SQLException{
        return getUser(String.format(GET_USER_BY_PHONE, phone));
    }

    private static User getUser(String query) throws SQLException {
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()){
                return new User(rs.getLong(ID),
                        rs.getString(LOGIN),
                        rs.getString(PASSWORD),
                        rs.getString(ADDRESS),
                        rs.getString(PHONE));
            }
        }
        return null;
    }

    public static void insertAuthorizedUser(AuthorizedUser authorizedUser) throws SQLException {
        try (PreparedStatement statement = JdbcUtils.getConnection()
                .prepareStatement(INSERT_AUTHORIZED_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, authorizedUser.getId());
            statement.setString(2, authorizedUser.getToken().toString());
            statement.executeUpdate();
        }
    }

    public static Long getUserIdByToken(UUID token) throws SQLException {
        String query = String.format(GET_USER_BY_TOKEN, token);
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()){
                return rs.getLong(ID);
            }
        }
        return null;
    }


    public static List<Account> getAllUserAccounts(long id) throws SQLException {
        String query = String.format(GET_USER_ACCOUNTS, id);
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query);
             ResultSet rs = statement.executeQuery(query)){
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                accounts.add(new Account(rs.getString(ID),
                        rs.getLong(CLIENT_ID),
                        rs.getBigDecimal(AMOUNT),
                        rs.getString(ACC_CODE)));
            }
            return accounts;
        }
    }

    public static Account getAccountById(UUID accountId) throws SQLException {
        String query = String.format(GET_ACCOUNT, accountId);
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()){
                return new Account(rs.getString(ID),
                        rs.getLong(CLIENT_ID), rs.getBigDecimal(AMOUNT), rs.getString(ACC_CODE));
            }
        }
        return null;
    }

    public static String getAuthorizedUserById(Long id) throws SQLException {
        String query = String.format(GET_AUTHORIZED_USER_BY_ID, id);
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            if (rs.next()){
                return rs.getString(TOKEN);
            }
        }
        return null;
    }

    public static void updateAccountAmount(UUID accountId, BigDecimal amount) throws SQLException {
        String query = String.format(UPDATE_ACCOUNT_AMOUNT, accountId);
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query)) {
            statement.setBigDecimal(1, amount);
            statement.executeUpdate();
        }
    }

    public static List<Operation> getUserOperationsList(Long userId) throws SQLException {
        String query = String.format(GET_USER_OPERATIONS, userId, userId);
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query);
             ResultSet rs = statement.executeQuery(query)){
            List<Operation> operations = new ArrayList<>();
            while (rs.next()) {
                operations.add(new Operation(rs.getLong(ID),
                        rs.getDate(OPERATION_DATE).toString(),
                        rs.getString(ACC_CODE),
                        rs.getString(ACCOUNT_FROM),
                        rs.getString(ACCOUNT_TO),
                        rs.getBigDecimal(AMOUNT),
                        rs.getBigDecimal(AMOUNT_BEFORE),
                        rs.getBigDecimal(AMOUNT_AFTER),
                        OperationType.valueOf(rs.getString(OPERATION_TYPE))
                ));
            }
            return operations;
        }
    }

    public static void insertOperation(Operation operation) throws SQLException {
        try (PreparedStatement statement =
                     JdbcUtils.getConnection().prepareStatement(INSERT_OPERATION, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, operation.getDate());
            statement.setString(2, operation.getAccCode().toString());
            statement.setString(3,
                    (operation.getAccountFrom() == null) ? null : operation.getAccountFrom().toString());
            statement.setString(4, operation.getAccountTo().toString());
            statement.setBigDecimal(5, operation.getAmount());
            statement.setBigDecimal(6, operation.getAmountBefore());
            statement.setBigDecimal(7, operation.getAmountAfter());
            statement.setString(8, operation.getType().name());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                operation.setId(rs.getInt(1));
            }
        }
    }

    public static void deleteUsers() throws SQLException {
        delete(DELETE_USERS);
    }

    public static void deleteOperations() throws SQLException {
        delete(DELETE_OPERATIONS);
    }

    public static void deleteAuthorizedUser(UUID token) throws SQLException {
        delete(String.format(DELETE_AUTHORIZED_USER, token.toString()));
    }

    private static void delete(String query) throws SQLException {
        try (PreparedStatement statement = JdbcUtils.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
}
