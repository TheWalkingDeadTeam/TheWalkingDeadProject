package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreApplicationDAO extends AbstractPostgreDAO<Application, Integer> implements ApplicationDAO {
    public  PostgreApplicationDAO(Connection connection){
        super(connection);
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
    public String getAllQuery() {
        return null;
    }

    @Override
    protected List<Application> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Application object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Application object) throws DAOException {

    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, Application object) throws DAOException {

    }

    @Override
    public Application getApplicationByUserCES(Integer user_id, Integer ces_id) {
        return null;
    }

    @Override
    public Application create(Application object) throws DAOException {
        return null;
    }
}
