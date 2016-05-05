package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;

/**
 * Created by Pavel on 21.04.2016.
 */
public interface UserDAO {
    User findByEmail(String email) throws DAOException;

    void createUser(User user) throws DAOException;

}
