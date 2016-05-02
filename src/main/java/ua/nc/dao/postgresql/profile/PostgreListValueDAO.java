package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ListValueDAO;
import ua.nc.entity.profile.ListValue;
import ua.nc.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreListValueDAO extends AbstractPostgreDAO<ListValue, Integer> implements ListValueDAO {
    public PostgreListValueDAO(Connection connection){
        super(connection);
    }

    private static String getAllListListValueQuery = "SELECT * FROM list_value WHERE list_id = ?";

    @Override
    public List<ListValue> getAllListListValue(Integer list_id) throws DAOException {
        List<ListValue> result;
        try (PreparedStatement statement = connection.prepareStatement(getAllListListValueQuery)){
            statement.setInt(1, list_id);
            result = parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    private class PersistListValue extends ListValue {
        public PersistListValue(int listID, double valueDouble, String valueText) {
            super(listID, valueDouble, valueText);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM list_value WHERE list_value.list_value_id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO list_value (list_id, value_double, value_text) VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE list SET list_value.double_value = ?, list_value.text_value = ? WHERE list_value.list_value_id = ?;";
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
                        rs.getDouble("value_double"),rs.getString("value_text"));
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
            statement.setDouble(2, object.getValueDouble());
            statement.setString(3, object.getValueText());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ListValue object) throws DAOException {
        try {
            statement.setDouble(1, object.getValueDouble());
            statement.setString(2, object.getValueText());
            statement.setInt(3, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, ListValue object) throws DAOException {
        try {
            statement.setInt(1, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ListValue create(ListValue object) throws DAOException {
        return persist(object);
    }
}
