package ua.nc.factory;

import ua.nc.dao.MailDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.factory.type.DataBaseType;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(DataBaseType type) {
        switch (type) {
            case POSTGRESQL: {
                return new PostgreDAOFactory();
            }
            default: {
                return new PostgreDAOFactory();
            }
        }
    }

    public abstract UserDAO getUserDAO();

    public abstract RoleDAO getRoleDAO();

    public abstract MailDAO getMailDAO();
}
