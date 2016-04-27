package ua.nc.dao.VDanchul.factories;

import ua.nc.dao.VDanchul.dao.GenericDAO;
import ua.nc.dao.VDanchul.dao.specific.PostgreFieldDAO;
import ua.nc.dao.VDanchul.entities.Field;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rangar on 24.04.2016.
 */
public class PostgreDAOFactory implements DAOFactory.DaoFactory<ConnectionPool> {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool(DataBaseType.POSTGRESQL);

    private Map<Class, DaoCreator> creators;

    @Override
    public ConnectionPool getContext() throws DAOException {
        return null;
    }

    @Override
    public GenericDAO getDao(ConnectionPool connectionPool, Class dtoClass) throws DAOException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new DAOException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connectionPool);
    }

    public PostgreDAOFactory() {
        creators = new HashMap<Class, DaoCreator>();
        creators.put(Field.class, new DaoCreator<ConnectionPool>() {
            @Override
            public GenericDAO create(ConnectionPool connectionPool) {
                return new PostgreFieldDAO(connectionPool);
            }
        });
    }
}
