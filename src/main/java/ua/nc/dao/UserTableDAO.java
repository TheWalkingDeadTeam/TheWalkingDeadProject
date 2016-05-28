package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.UserRow;

import java.util.List;

/**
 * Created by Rangar on 28.05.2016.
 */
public interface UserTableDAO {
    Integer getUsersCount(String pattern) throws DAOException;

    List<UserRow> getUsersTable() throws DAOException;

    List<UserRow> getUsersTable(Integer limit, Integer offset) throws DAOException;

    List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy) throws DAOException;

    List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy, Boolean asc) throws DAOException;

    List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy ,String pattern) throws DAOException;

    List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException;
}
