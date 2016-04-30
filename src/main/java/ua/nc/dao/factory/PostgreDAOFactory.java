package ua.nc.dao.factory;

import ua.nc.dao.MailDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.postgresql.PostgreMailDAO;
import ua.nc.dao.postgresql.PostgreRoleDAO;
import ua.nc.dao.postgresql.PostgreUserDAO;

/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreDAOFactory extends DAOFactory {

    @Override
    public UserDAO getUserDAO() {
        return new PostgreUserDAO();
    }

    @Override
    public RoleDAO getRoleDAO() {
        return new PostgreRoleDAO();
    }

    @Override
    public MailDAO getMailDAO() {
        return new PostgreMailDAO();
    }
}
