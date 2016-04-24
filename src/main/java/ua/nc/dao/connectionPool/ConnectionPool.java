package ua.nc.dao.connectionPool;

import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.postgresql.PostgreConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class ConnectionPool {

    public static ConnectionPool getConnectionPool(DataBaseType type) {
        switch (type) {
            case POSTGRESQL: {
                return new PostgreConnectionPool();
            }
            default: {
                return new PostgreConnectionPool();
            }
        }
    }

    public abstract Connection getConnection() throws DAOException, SQLException;

    public abstract void putConnection(Connection connection) throws DAOException, SQLException;
}
