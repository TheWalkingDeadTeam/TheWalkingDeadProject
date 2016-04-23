package ua.nc.dao.postgresql;

import org.postgresql.ds.PGPoolingDataSource;
import ua.nc.dao.connectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pavel on 18.04.2016.
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


    public static synchronized PostgreConnectionPool getInstance() {
        if (instance == null) {
            return new PostgreConnectionPool();
        } else {
            return instance;
        }
    }


    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw e;
        }
        return connection;
    }


    public void putConnection(Connection connection) throws SQLException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }
}
