package ua.nc.dao.postgresql.profile;

import ua.nc.dao.FieldValueDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.FieldValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreFieldValueDAO implements FieldValueDAO {

    private static final String getFieldValueByUserCESFieldQuery = "SELECT * FROM field_value WHERE Application_id = " +
            "(SELECT Application_id from Application WHERE User_id = ? AND CES_id = ?) AND Field_id = ?";
    private static final String updateQuery = "UPDATE field_value " +
            "SET value_text = ?, value_double = ?, value_date = ? " +
            "WHERE Application_id = ? AND Field_id = ?";
    private static final String createQuery = "INSERT INTO field_value " +
            "(field_id, application_id, value_text, value_double, value_date, list_value_id) " +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String deleteQuery = "DELETE FROM field_value WHERE Application_id = " +
            "(SELECT Application_id from Application WHERE User_id = ? AND CES_id = ?) AND Field_id = ?";

    private Connection connection;

    public PostgreFieldValueDAO(Connection connection){
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
    public void update(FieldValue fieldValue) {

    }

    @Override
    public FieldValue create(FieldValue fieldValue) {
        return null;
    }

    @Override
    public void deleteMultiple(Integer user_id, Integer ces_id, Integer field_id) {

    }

    private List<FieldValue> parseResultSet(ResultSet rs){
        return null;
    }
}
