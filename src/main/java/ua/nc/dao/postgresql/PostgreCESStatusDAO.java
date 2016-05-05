package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.CESStatusDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CESStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 04.05.2016.
 */
public class PostgreCESStatusDAO extends AbstractPostgreDAO<CESStatus, Integer> implements CESStatusDAO {
    public  PostgreCESStatusDAO(Connection connection){
        super(connection);
    }

    private class PersistCESStatus extends CESStatus{
        public PersistCESStatus(String name) {
            super(name);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FORM ces_status WHERE ces_status_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO ces_status (name) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE ces_status SET name = ? WHERE ces_status_id = ?";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FORM ces_status";
    }

    @Override
    protected List<CESStatus> parseResultSet(ResultSet rs) throws DAOException {
        List<CESStatus> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistCESStatus cesStatus = new PersistCESStatus(rs.getString("name"));
                cesStatus.setId(rs.getInt("ces_status_id"));
                result.add(cesStatus);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CESStatus object) throws DAOException {
        try {
            statement.setString(1, object.getName());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CESStatus object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public CESStatus create(CESStatus object) throws DAOException {
        return persist(object);
    }
}
