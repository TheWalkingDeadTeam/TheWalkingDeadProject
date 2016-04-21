package ua.nc.db;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pavel on 18.04.2016.
 */
public class PostgresConnectionPool {
    private static PostgresConnectionPool instance;
    private PGPoolingDataSource dataSource;

    public PostgresConnectionPool() {
        dataSource = new PGPoolingDataSource();
        dataSource.setServerName("130.211.149.11");
        dataSource.setDatabaseName("wd");
        dataSource.setUser("postgres");
        dataSource.setPassword("netcrackerpwd");
    }


    public static synchronized PostgresConnectionPool getInstance() {
        if (instance == null) {
            return new PostgresConnectionPool();
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
