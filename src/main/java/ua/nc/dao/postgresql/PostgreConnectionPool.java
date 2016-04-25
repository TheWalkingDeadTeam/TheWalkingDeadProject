package ua.nc.dao.postgresql;

import org.postgresql.ds.PGPoolingDataSource;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pavel on 25.04.2016.
 */
public class PostgreConnectionPool extends ConnectionPool {

    private static PostgreConnectionPool instance;
    private PGPoolingDataSource dataSource;

    public PostgreConnectionPool() {
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


    public Connection getConnection() throws SQLException, DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return connection;
    }


    public void putConnection(Connection connection) throws SQLException, DAOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
