package ua.nc.dao.factory.factories;

import ua.nc.dao.GenericDAO;
import ua.nc.dao.exception.DAOException;

import java.sql.Connection;

/**
 * Created by Rangar on 24.04.2016.
 */
public class DAOFactory {
    public interface DaoFactory<Context> {

        public Connection getConnection() throws DAOException;

        public interface DaoCreator<Context> {
            public GenericDAO create(Context context);
        }
        public Context getContext() throws DAOException;
        /** returns some DAO */
        public GenericDAO getDao(Context context, Class dtoClass) throws DAOException;
    }
}
