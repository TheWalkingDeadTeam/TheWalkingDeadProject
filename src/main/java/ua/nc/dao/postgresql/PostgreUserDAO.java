package ua.nc.dao.postgresql;

import ua.nc.dao.AppSetting;
import ua.nc.dao.UserDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreUserDAO extends UserDAO {
    /*    private static final Logger LOGGER = Logger.getLogger(PostgreUserDAO.class);*/
    private final ConnectionPool connectionPool;

    public PostgreUserDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public User findByEmail(String email) throws DAOException {
        String sql = AppSetting.get("user.findByEmail");
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            System.out.println(user.getName());
        } catch (SQLException e) {
            System.out.println("User with " + email + " not find in DB");
            throw new DAOException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connectionPool.putConnection(connection);
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
        return user;
    }

    @Override
    public void createUser(User user) throws DAOException {
        String sql = AppSetting.get("user.createUser");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("User with name" + user.getName() + "  not created");
            throw new DAOException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connectionPool.putConnection(connection);
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
