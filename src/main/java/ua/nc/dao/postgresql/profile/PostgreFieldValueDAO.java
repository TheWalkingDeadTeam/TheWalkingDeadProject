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

    private static final String getFieldValueByUserCESFieldQuery = "SELECT * FROM field_value WHERE Application_id = " +
            "(SELECT Application_id from Application WHERE system_user_id = ? AND CES_id = ?) AND Field_id = ?";
    private static final String updateQuery = "UPDATE field_value " +
            "SET value_text = ?, value_double = ?, value_date = ? " +
            "WHERE Application_id = ? AND Field_id = ?";
    private static final String createQuery = "INSERT INTO field_value " +
            "(field_id, application_id, value_text, value_double, value_date, list_value_id) " +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String deleteQuery = "DELETE FROM field_value WHERE Application_id = " +
            "(SELECT Application_id from Application WHERE system_user_id = ? AND CES_id = ?) AND Field_id = ?";

    private Connection connection;

    public PostgreFieldValueDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<FieldValue> getFieldValueByUserCESField(Integer user_id, Integer ces_id, Integer field_id) throws DAOException {
        List<FieldValue> result;
        try (PreparedStatement statement = connection.prepareStatement(getFieldValueByUserCESFieldQuery)) {
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
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, fieldValue.getValueText());
            statement.setDouble(2, fieldValue.getValueDouble());
            statement.setDate(3, new java.sql.Date(fieldValue.getValueDate().getTime()));
            statement.setInt(4, fieldValue.getApplicationID());
            statement.setInt(5, fieldValue.getFieldID());
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
        try (PreparedStatement statement = connection.prepareStatement(createQuery)) {
            statement.setInt(1, fieldValue.getFieldID());
            statement.setInt(2, fieldValue.getApplicationID());
            statement.setString(3, fieldValue.getValueText());
            statement.setDouble(4, fieldValue.getValueDouble());
            statement.setDate(5, new java.sql.Date(fieldValue.getValueDate().getTime()));
            statement.setInt(6, fieldValue.getListValueID());
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
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
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
                        rs.getString("value_text"), rs.getDouble("value_double"),
                        rs.getDate("value_date"), rs.getInt("list_value_id"));
                result.add(fieldValue);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }
}
