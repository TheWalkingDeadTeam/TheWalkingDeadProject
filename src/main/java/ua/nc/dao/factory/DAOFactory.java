package ua.nc.dao.factory;

import ua.nc.dao.MailDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.postgresql.PostgreDAOFactory;

import java.sql.Connection;


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

    public abstract Connection getConnection();

    public abstract UserDAO getUserDAO(Connection connection);

    public abstract RoleDAO getRoleDAO(Connection connection);

    public abstract MailDAO getMailDAO(Connection connection);
}
