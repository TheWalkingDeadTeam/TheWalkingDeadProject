package ua.nc.dao;

import org.apache.log4j.Logger;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public abstract class GenericDAO<T, PK extends Serializable> {

    public abstract T get(int id) throws DAOException;

    public abstract int create(User user) throws DAOException;

    public abstract void update(User user) throws DAOException;

    public abstract void delete(int id) throws DAOException;

}
