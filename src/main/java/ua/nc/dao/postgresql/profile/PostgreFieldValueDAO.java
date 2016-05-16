package ua.nc.dao.postgresql.profile;

import ua.nc.dao.FieldValueDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.FieldValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreFieldValueDAO implements FieldValueDAO {

    private static final String GET_FIELD_VALUE_BY_USER_CES_FIELD_QUERY = "SELECT * FROM field_value WHERE Application_id = " +
            "(SELECT Application_id from Application WHERE system_user_id = ? AND CES_id = ?) AND Field_id = ?";
    private static final String UPDATE_QUERY = "UPDATE field_value " +
            "SET value_text = ?, value_double = ?, value_date = ?, list_value_id = ? " +
            "WHERE Application_id = ? AND Field_id = ?";
    private static final String CREATE_QUERY = "INSERT INTO field_value " +
            "(field_id, application_id, value_text, value_double, value_date, list_value_id) " +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String DELETE_QUERY = "DELETE FROM field_value WHERE Application_id = " +
            "(SELECT Application_id from Application WHERE system_user_id = ? AND CES_id = ?) AND Field_id = ?";

    private Connection connection;

    public PostgreFieldValueDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FieldValue> getFieldValueByUserCESField(Integer user_id, Integer ces_id, Integer field_id) throws DAOException {
        List<FieldValue> result;
        try (PreparedStatement statement = connection.prepareStatement(GET_FIELD_VALUE_BY_USER_CES_FIELD_QUERY)) {
            statement.setInt(1, user_id);
            statement.setInt(2, ces_id);
            statement.setInt(3, field_id);
            result = parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public void update(FieldValue fieldValue) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, fieldValue.getValueText());
            statement.setObject(2, fieldValue.getValueDouble());
            if (fieldValue.getValueDate() == null){
                statement.setDate(3, null);
            } else {
                statement.setDate(3, new java.sql.Date(fieldValue.getValueDate().getTime()));
            }
            statement.setObject(4, fieldValue.getListValueID());
            statement.setInt(5, fieldValue.getApplicationID());
            statement.setInt(6, fieldValue.getFieldID());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public FieldValue create(FieldValue fieldValue) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setInt(1, fieldValue.getFieldID());
            statement.setInt(2, fieldValue.getApplicationID());
            statement.setString(3, fieldValue.getValueText());
            statement.setObject(4, fieldValue.getValueDouble());
            if (fieldValue.getValueDate() == null){
                statement.setDate(5, null);
            } else {
                statement.setDate(5, new java.sql.Date(fieldValue.getValueDate().getTime()));
            }
            statement.setObject(6, fieldValue.getListValueID());
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return fieldValue;
    }

    @Override
    public void deleteMultiple(Integer user_id, Integer ces_id, Integer field_id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, user_id);
            statement.setInt(2, ces_id);
            statement.setInt(3, field_id);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private List<FieldValue> parseResultSet(ResultSet rs) throws DAOException {
        List<FieldValue> result = new ArrayList<>();
        try {
            while (rs.next()) {
                FieldValue fieldValue = new FieldValue(rs.getInt("field_id"), rs.getInt("application_id"),
                        rs.getString("value_text"),(Double) rs.getObject("value_double"),
                        rs.getDate("value_date"),(Integer) rs.getObject("list_value_id"));
                result.add(fieldValue);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }
}
