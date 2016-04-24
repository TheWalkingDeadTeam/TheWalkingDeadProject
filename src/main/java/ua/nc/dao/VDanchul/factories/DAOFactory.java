package ua.nc.dao.VDanchul.factories;

import ua.nc.dao.VDanchul.dao.GenericDAO;
import ua.nc.dao.exception.DAOException;

/**
 * Created by Rangar on 24.04.2016.
 */
public class DAOFactory {
    public interface DaoFactory<Context> {

        public interface DaoCreator<Context> {
            public GenericDAO create(Context context);
        }
        public Context getContext() throws DAOException;
        /** returns some DAO */
        public GenericDAO getDao(Context context, Class dtoClass) throws DAOException;
    }
}
