package ua.nc.dao.VDanchul.dao.specific;

import ua.nc.dao.VDanchul.dao.AbstractPostgreDAO;
import ua.nc.dao.VDanchul.entities.Field;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Rangar on 24.04.2016.
 */
public class FieldDAO extends AbstractPostgreDAO<Field, Integer> {
    public FieldDAO(ConnectionPool connectionPool){
        super(connectionPool);
    }

    @Override
    public String getSelectQuery() {
        return null;
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return null;
    }

    @Override
    protected List<Field> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Field object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Field object) throws DAOException {

    }

    @Override
    public Field create() throws DAOException {
        return null;
    }
}
