package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.FieldDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.Field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 24.04.2016.
 */
public class PostgreFieldDAO extends AbstractPostgreDAO<Field, Integer> implements FieldDAO {
    private static final String GET_FIELDS_FOR_CES_QUERY = "SELECT * FROM field f " +
            "JOIN ces_field cf ON f.field_id = cf.field_id WHERE cf.ces_id = ?";

    public PostgreFieldDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<Field> getFieldsForCES(Integer ces_id) throws DAOException {
        List<Field> result;
        try (PreparedStatement statement = connection.prepareStatement(GET_FIELDS_FOR_CES_QUERY)) {
            statement.setInt(1, ces_id);
            ResultSet rs = statement.executeQuery();
            result = parseResultSet(rs);
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM field WHERE field_id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO field (name, field_type_id, multiple_choice, order_num, list_id) VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE field SET field.name = ?, field_type_id = ?, multiple_choice = ?, order_num = ?, list_id = ? " +
                "WHERE field.field_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM field";
    }

    @Override
    protected List<Field> parseResultSet(ResultSet rs) throws DAOException {
        List<Field> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistField field = new PersistField(rs.getString("name"),
                        rs.getInt("field_type_id"), rs.getBoolean("multiple_choice"),
                        rs.getInt("order_num"), (Integer) rs.getObject("list_id"));
                field.setId(rs.getInt("field_id"));
                result.add(field);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Field object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getFieldTypeID());
            statement.setBoolean(3, object.getMultipleChoice());
            statement.setInt(4, object.getOrderNum());
            statement.setObject(5, object.getListTypeID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Field object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getFieldTypeID());
            statement.setBoolean(3, object.getMultipleChoice());
            statement.setInt(4, object.getOrderNum());
            statement.setObject(5, object.getListTypeID());
            statement.setInt(6, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Field create(Field field) throws DAOException {
        return persist(field);
    }

    private class PersistField extends Field {
        public PersistField(String name, int fieldTypeID, boolean multipleChoice, int orderNum, Integer listID) {
            super(name, fieldTypeID, multipleChoice, orderNum, listID);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }
}
