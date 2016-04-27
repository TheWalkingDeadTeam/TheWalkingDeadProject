package ua.nc.dao.VDanchul.dao.specific;

import ua.nc.dao.VDanchul.dao.AbstractPostgreDAO;
import ua.nc.dao.VDanchul.entities.ListType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreListDAO extends AbstractPostgreDAO<ListType, Integer> {
    public PostgreListDAO(ConnectionPool connectionPool){
        super(connectionPool);
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
        LinkedList<ListType> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistListType lt = new PersistListType(rs.getString("name"));
                lt.setID(rs.getInt("list_id"));
                result.add(lt);
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
            statement.setInt(2, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, ListType object) throws DAOException {
        try {
            statement.setInt(1, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ListType create(ListType object) throws DAOException {
        return persist(object);
    }
}
