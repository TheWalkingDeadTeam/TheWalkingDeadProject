package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.entity.profile.ListType;
import ua.nc.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreListTypeDAO extends AbstractPostgreDAO<ListType, Integer> {
    public PostgreListTypeDAO(Connection connection){
        super(connection);
    }

    private class PersistListType extends ListType {
        public PersistListType(String name) {
            super(name);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM list WHERE list.list_id = ?;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO list (name) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE list SET list.name = ? WHERE list.list_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM list";
    }

    @Override
    protected java.util.List<ListType> parseResultSet(ResultSet rs) throws DAOException {
        List<ListType> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistListType listType = new PersistListType(rs.getString("name"));
                listType.setID(rs.getInt("list_id"));
                result.add(listType);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ListType object) throws DAOException {
        try {
            statement.setString(1, object.getName());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ListType object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, ListType object) throws DAOException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ListType create(ListType object) throws DAOException {
        return persist(object);
    }
}
