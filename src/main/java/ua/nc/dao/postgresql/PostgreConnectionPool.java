package ua.nc.dao.postgresql;

import org.postgresql.ds.PGPoolingDataSource;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pavel on 18.04.2016.
 */
public class PostgreConnectionPool extends ConnectionPool {

    private static volatile PostgreConnectionPool instance;
    private PGPoolingDataSource dataSource;

    private PostgreConnectionPool() {
        dataSource = new PGPoolingDataSource();
        dataSource.setServerName("130.211.149.11");
        dataSource.setDatabaseName("wd");
        dataSource.setUser("postgres");
        dataSource.setPassword("netcrackerpwd");
    }

    public static PostgreConnectionPool getInstance() {
        PostgreConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (PostgreConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PostgreConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return connection;
    }


    public void putConnection(Connection connection) throws DAOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}