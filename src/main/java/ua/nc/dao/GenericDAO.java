package ua.nc.dao;

import org.apache.log4j.Logger;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.entity.User;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class GenericDAO<T, PK extends Serializable> {

    private static final Logger LOGGER = Logger.getLogger(GenericDAO.class);

    public abstract T get(int id) throws DAOException, SQLException;

    public abstract int create(User user) throws DAOException;

    public abstract void update(User user) throws DAOException;

    public abstract void delete(int id) throws DAOException;

    public void closeConnStmt(ConnectionPool connectionPool, Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connectionPool.putConnection(connection);
        } catch (SQLException e) {
            LOGGER.error(e);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
    }
}
