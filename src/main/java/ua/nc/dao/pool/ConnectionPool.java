package ua.nc.dao.pool;

import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.db.PostgresConnectionPool;
import java.sql.Connection;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class ConnectionPool {
    public static ConnectionPool getConnectionPool(DataBaseType type) {
        switch (type) {
            case POSTGRESQL: {
                return new PostgresConnectionPool();
            }
            default: {
                return new PostgresConnectionPool();
            }
        }
    }

    public abstract Connection getConnection() throws DAOException;

    public abstract void putConnection(Connection connection) throws DAOException;
}
