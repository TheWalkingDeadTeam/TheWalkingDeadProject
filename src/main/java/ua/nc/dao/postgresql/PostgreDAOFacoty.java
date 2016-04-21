package ua.nc.dao.postgresql;

import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.pool.ConnectionPool;

/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreDAOFacoty extends DAOFactory {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool(DataBaseType.POSTGRESQL);
    @Override
    public UserDAO getUserDAO() {
        return new PostgreUserDAO(connectionPool);
    }
}
