package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ListValueDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.ListValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreListValueDAO extends AbstractPostgreDAO<ListValue, Integer> implements ListValueDAO {
    private static String getAllListListValueQuery = "SELECT * FROM list_value WHERE list_id = ?";

    public PostgreListValueDAO(Connection connection) {
        super(connection);
    }

    @Override
    public List<ListValue> getAllListListValue(Integer list_id) throws DAOException {
        List<ListValue> result;
        try (PreparedStatement statement = connection.prepareStatement(getAllListListValueQuery)) {
            statement.setInt(1, list_id);
            result = parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM list_value WHERE list_value.list_value_id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO list_value (list_id, value_text) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE list SET list_value.text_value = ? WHERE list_value.list_value_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM list_value";
    }

    @Override
    protected List<ListValue> parseResultSet(ResultSet rs) throws DAOException {
        List<ListValue> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistListValue listValue = new PersistListValue(rs.getInt("list_value_id"),
                        rs.getString("value_text"));
                listValue.setId(rs.getInt("list_value_id"));
                listValue.setListID(rs.getInt("list_id"));
                result.add(listValue);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ListValue object) throws DAOException {
        try {
            statement.setInt(1, object.getListID());
            statement.setString(2, object.getValueText());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ListValue object) throws DAOException {
        try {
            statement.setString(1, object.getValueText());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ListValue create(ListValue object) throws DAOException {
        return persist(object);
    }

    private class PersistListValue extends ListValue {
        public PersistListValue(int listID, String valueText) {
            super(listID, valueText);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }
}
