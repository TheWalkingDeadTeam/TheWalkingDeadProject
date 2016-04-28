package ua.nc.dao;

import ua.nc.exception.DAOException;

import java.io.Serializable;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class GenericDAO<T, PK extends Serializable> {
    /**
     * Basic CRUD operations
     * @param entity stub
     * @return
     * @throws DAOException
     */

    public abstract T create(T entity) throws DAOException;

    public abstract T get(PK id) throws DAOException;

    public abstract T update(T entity) throws DAOException;

    public abstract void delete(T entity) throws DAOException;

}
