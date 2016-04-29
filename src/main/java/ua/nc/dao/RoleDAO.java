package ua.nc.dao;

import ua.nc.exception.DAOException;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.util.Set;

/**
 * Created by Pavel on 22.04.2016.
 */
public abstract class RoleDAO {
    public abstract Role findByName(String name) throws DAOException;

    public abstract Set<Role> findAllByEmail(String email) throws DAOException;

    public abstract void setRoleToUser(Set<Role> roles, User user) throws DAOException;
}
