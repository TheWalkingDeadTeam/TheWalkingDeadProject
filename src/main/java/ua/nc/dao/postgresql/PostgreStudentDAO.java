package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgreStudentDAO extends UserDAO {

    private static final Logger LOGGER = Logger.getLogger(PostgreStudentDAO.class);
    private final String sqlFindByEmail = "SELECT * FROM public.user u WHERE u.email = ?";
    private final String sqlFindRoleByEmail = "SELECT r.name FROM public.user u " +
            "JOIN public.user_role ur on u.user_id = ur.user_id " +
            "JOIN public.role r ON ur.role_id = r.role_id " +
            "WHERE u.email = ?";
    private final String sqlCreateUser = "INSERT INTO public.user(name, email, password) " +
            "VALUES (?, ?, ?)";
    private final String sqlCreateRoleToUser = "INSERT INTO public.user_role(user_id, role_id) " +
            "VALUES (?, ?)";
    private final String sqlGetById = "SELECT * FROM public.user u WHERE u.user_id = ?";
    private final String sqlFindUserByName = "SELECT * FROM public.user u WHERE u.name = ?";
    private final String sqlDeleteUserById = "DELETE FROM public.user u WHERE u.user_id = ?";
    ConnectionPool connectionPool;
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet = null;

    public PostgreStudentDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<User> findUsersByName(String name) throws DAOException, SQLException {
        List<User> users = new ArrayList<User>();
        User user = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlFindUserByName);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                RoleDAO roleDAO = new PostgreRoleDAO(connectionPool);
                user.setRoles(roleDAO.findByEmail(resultSet.getString("email")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
        return users;
    }

    @Override
    public User findUserByEmail(String email) throws DAOException {
        User user = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlFindByEmail);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setId(resultSet.getInt("user_id"));
                user.setPassword(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public User get(int id) throws DAOException, SQLException {
        User user = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlGetById);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                RoleDAO roleDAO = new PostgreRoleDAO(connectionPool);
                user.setRoles(roleDAO.findByEmail(resultSet.getString("email")));
                return user;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public int create(User user) throws DAOException {
        int id = 0;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlCreateUser, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
        return id;
    }

    @Override
    public void update(User user) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {// toDo make it
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlDeleteUserById);
            preparedStatement.setInt((Integer) 1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
    }
}
