package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.util.Set;

/**
 * Created by Pavel on 22.04.2016.
 */
public interface RoleDAO extends GenericDAO<Role, Integer> {
    Role findByName(String name) throws DAOException;

    Set<Role> findByEmail(String email) throws DAOException;

    void setRolesToUser(Set<Role> roles, User user) throws DAOException;
    void removeRolesFromUser(User user) throws DAOException;
}
