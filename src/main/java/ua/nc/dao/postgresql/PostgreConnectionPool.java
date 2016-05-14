package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import org.postgresql.ds.PGPoolingDataSource;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Pavel on 25.04.2016.
 */
public class PostgreConnectionPool extends ConnectionPool {
    private final static Logger LOGGER = Logger.getLogger(PostgreConnectionPool.class);
    private static volatile PostgreConnectionPool instance;
    private final String SERVER_NAME = "localhost:5432";//"23.251.143.100";
    private final String DATABASE_NAME = "postgres";//"WD_Project";
    private final String USER = "postgres";
    private final String PASSWORD = "123488";//"netcrackerpwd";
    private PGPoolingDataSource dataSource;

    private PostgreConnectionPool() {
        dataSource = new PGPoolingDataSource();
        dataSource.setServerName(SERVER_NAME);
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
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
            LOGGER.debug("Connection established");
        } catch (SQLException e) {
            LOGGER.warn("Connection denied");
            throw new DAOException(e);
        }
        return connection;
    }


    public void putConnection(Connection connection) throws DAOException {
        try {
            connection.close();
            LOGGER.debug("Connection closed");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
}
