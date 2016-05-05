package ua.nc.dao.postgresql.profile;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ListTypeDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.ListType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreListTypeDAO extends AbstractPostgreDAO<ListType, Integer> implements ListTypeDAO {
    public PostgreListTypeDAO(Connection connection) {
        super(connection);
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
                listType.setId(rs.getInt("list_id"));
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
    public ListType create(ListType object) throws DAOException {
        return persist(object);
    }

    private class PersistListType extends ListType {
        public PersistListType(String name) {
            super(name);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }
}
