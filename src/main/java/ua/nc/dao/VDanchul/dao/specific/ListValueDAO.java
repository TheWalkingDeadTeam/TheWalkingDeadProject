package ua.nc.dao.VDanchul.dao.specific;

import ua.nc.dao.VDanchul.dao.AbstractPostgreDAO;
import ua.nc.dao.VDanchul.entities.ListValue;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class ListValueDAO extends AbstractPostgreDAO<ListValue, Integer> {
    public ListValueDAO(ConnectionPool connectionPool){
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
    protected List<ListValue> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ListValue object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ListValue object) throws DAOException {

    }

    @Override
    public ListValue create() throws DAOException {
        return null;
    }
}
