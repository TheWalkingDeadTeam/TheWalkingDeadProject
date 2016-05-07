package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.FieldTypeDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.FieldType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreFieldTypeDAO extends AbstractPostgreDAO<FieldType, Integer> implements FieldTypeDAO {
    public PostgreFieldTypeDAO(Connection connection) {
        super(connection);
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
        List<FieldType> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistFieldType fieldType = new PersistFieldType(rs.getString("name"));
                fieldType.setId(rs.getInt("field_type_id"));
                result.add(fieldType);
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
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public FieldType create(FieldType object) throws DAOException {
        return persist(object);
    }

    private class PersistFieldType extends FieldType {
        public PersistFieldType(String name) {
            super(name);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }
}
