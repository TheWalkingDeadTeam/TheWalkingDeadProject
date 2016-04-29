package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.entity.profile.Field;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rangar on 24.04.2016.
 */
public class PostgreFieldDAO extends AbstractPostgreDAO<Field, Integer> {
    public PostgreFieldDAO(ConnectionPool connectionPool){
        super(connectionPool);
    }

    private class PersistField extends Field {
        public PersistField(int cesID, String name, int fieldTypeID, boolean multipleChoice, int orderNum, int listID) {
            super(cesID, name, fieldTypeID, multipleChoice, orderNum, listID);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM field WHERE field_id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO field (ces_id, name, field_type_id, multiple_choice, order_num, list_id) VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE field SET field.name = ? WHERE field.field_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM field";
    }

    @Override
    protected List<Field> parseResultSet(ResultSet rs) throws DAOException {
        LinkedList<Field> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistField field = new PersistField(rs.getInt("ces_id"), rs.getString("name"),
                        rs.getInt("filed_type_id"), rs.getBoolean("multiple_choice"),
                        rs.getInt("order_num"), rs.getInt("list_id"));
                field.setID(rs.getInt("field_id"));
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
            statement.setInt(1, object.getCesID());
            statement.setString(2, object.getName());
            statement.setInt(3, object.getFieldTypeID());
            statement.setBoolean(4, object.getMultipleChoice());
            statement.setInt(5, object.getOrderNum());
            statement.setInt(6, object.getListTypeID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Field object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, Field object) throws DAOException {
        try {
            statement.setInt(1, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Field create(Field field) throws DAOException {
        return persist(field);
    }
}
