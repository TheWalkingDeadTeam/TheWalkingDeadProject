package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class UserDAO extends GenericDAO<User, Integer> {

    public abstract List<User> findUsersByName(String name) throws DAOException, SQLException;

    public abstract User findUserByEmail(String email) throws DAOException;

}
