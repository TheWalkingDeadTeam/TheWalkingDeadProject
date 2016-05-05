package ua.nc.dao.postgresql;

import ua.nc.dao.MailDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.Connection;

/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreDAOFactory extends DAOFactory {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool(DataBaseType.POSTGRESQL);


    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            System.out.println("CONNECTION ESTABLISHED");
        } catch (Exception e) {
            System.out.println("GET CONNECTION");
        }
        return connection;
    }

    @Override
    public void putConnection(Connection connection) {
        try {
            connectionPool.putConnection(connection);
        } catch (DAOException e){

        }
    }

    @Override
    public UserDAO getUserDAO(Connection connection) {
        return new PostgreUserDAO(connection);
    }

    @Override
    public RoleDAO getRoleDAO(Connection connection) {
        return new PostgreRoleDAO(connection);
    }

    @Override
    public MailDAO getMailDAO(Connection connection) {
        return new PostgreMailDAO(connection);
    }
}
