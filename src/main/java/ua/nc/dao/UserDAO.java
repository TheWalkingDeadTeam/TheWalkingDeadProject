package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class UserDAO {
    public abstract User findByEmail(String email) throws DAOException;

    public abstract void createUser(User user) throws DAOException;

    public abstract void updateUser(User user) throws DAOException;

}
