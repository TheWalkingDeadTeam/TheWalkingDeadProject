package ua.nc.dao;

import ua.nc.dao.exception.DAOException;

import java.io.Serializable;

/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class GenericDAO<T, PK extends Serializable> {
    public abstract T create() throws DAOException;
}
