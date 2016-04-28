package ua.nc.dao;

import ua.nc.entity.Identified;
import ua.nc.dao.exception.DAOException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rangar on 24.04.2016.
 */
public interface GenericDAO<T extends Identified, PK extends Serializable> {
    public T create(T object) throws DAOException;
    public T persist(T object) throws DAOException;
    public T read(PK key) throws DAOException;
    public void update(T object) throws DAOException;
    public List<T> getAll() throws DAOException;
}
