package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import ua.nc.dao.UserDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;


/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreUserDAO extends UserDAO {
    private static final Logger LOGGER = Logger.getLogger(PostgreUserDAO.class);

    public User findByEmail() throws DAOException {
        return null;
    }
}
