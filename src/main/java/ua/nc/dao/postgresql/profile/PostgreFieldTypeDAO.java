package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.entity.profile.FieldType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreFieldTypeDAO extends AbstractPostgreDAO<FieldType, Integer> {
    public PostgreFieldTypeDAO(ConnectionPool connectionPool){
        super(connectionPool);
    }

    private class PersistFieldType extends FieldType {
        public PersistFieldType(String name) {
            super(name);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM field_type WHERE field_type.field_type_id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO field_type (name) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE field_type SET field_type.name = ? WHERE field_type.field_type_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM field_type";
    }

    @Override
    protected List<FieldType> parseResultSet(ResultSet rs) throws DAOException {
        LinkedList<FieldType> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistFieldType lt = new PersistFieldType(rs.getString("name"));
                lt.setID(rs.getInt("list_id"));
                result.add(lt);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, FieldType object) throws DAOException {
        try {
            statement.setString(1, object.getName());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, FieldType object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, FieldType object) throws DAOException {
        try {
            statement.setInt(1, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public FieldType create(FieldType object) throws DAOException {
        return persist(object);
    }
}
