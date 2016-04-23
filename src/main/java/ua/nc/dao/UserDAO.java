package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;
import ua.nc.entity.enums.Role;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class UserDAO extends GenericDAO {

    public abstract List<User> findUsersByName (String name) throws DAOException;

    public abstract User findUserByEmail (String email) throws DAOException;

    public abstract Set<Role> findRoleByEmail(String email) throws SQLException;

    public abstract void createRoleToUser(int id, Role role) throws SQLException;

}
