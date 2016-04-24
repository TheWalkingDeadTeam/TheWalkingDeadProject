package ua.nc.dao.VDanchul.dao;

import ua.nc.dao.VDanchul.entities.Identified;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Rangar on 24.04.2016.
 */
public abstract class AbstractPostgreDAO<T extends Identified<PK>, PK extends Integer> implements GenericDAO<T, PK> {
    private ConnectionPool connectionPool;

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract List<T> parseResultSet(ResultSet rs) throws DAOException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DAOException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DAOException;

    @Override
    public T persist(T object) throws DAOException {
        T persistInstance;
        String sql = getCreateQuery();
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        sql = getSelectQuery() + " WHERE id = last_insert_id();";
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            List<T> list = parseResultSet(rs);
            if ((list == null) || (list.size() != 1)) {
                throw new DAOException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return persistInstance;
    }

    @Override
    public T read(PK key) throws DAOException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(sql)) {
            statement.setInt(1, (int)key);
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DAOException(e);
        }
        if (list == null || list.size() == 0) {
            throw new DAOException("Record with PK = " + key + " not found.");
        }
        if (list.size() > 1) {
            throw new DAOException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public void update(T object) throws DAOException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object); // заполнение аргументов запроса оставим на совесть потомков
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(T object) throws DAOException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getID());
            } catch (Exception e) {
                throw new DAOException(e);
            }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<T> getAll() throws DAOException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return list;
    }

    public AbstractPostgreDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
