package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class UserDAO extends AbstractDAO {
    public abstract User findByEmail() throws DAOException;

}
