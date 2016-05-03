package ua.nc.dao;

import ua.nc.entity.Identified;
import ua.nc.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Rangar on 24.04.2016.
 */

/**
 * Abstract class which provides basic CRUD using JDBC.
 *
 * @param <T>  object type
 * @param <PK> primary key type
 */
public abstract class AbstractPostgreDAO<T extends Identified<PK>, PK extends Integer> implements GenericDAO<T, PK> {
    protected Connection connection;

    /**
     * Returns sql query for getting some object.
     * <p/>
     * SELECT * FROM [Table] WHERE id = ?
     */
    public abstract String getSelectQuery();

    /**
     * Returns sql query for creating new row.
     * <p/>
     * INSERT INTO [Table] ([column, column, ...]) VALUES (?, ?, ...);
     */
    public abstract String getCreateQuery();

    /**
     * Returns sql query for updating a row.
     * <p/>
     * UPDATE [Table] SET [column = ?, column = ?, ...] WHERE id = ?;
     */
    public abstract String getUpdateQuery();

    /**
     * Returns sql query for getting all objects from a table.
     * <p/>
     * SELECT * FROM [Table]
     */
    public abstract String getAllQuery();

    /**
     * Parsing ResultSet and returning list of objects corresponding to the value of the ResultSet.
     */
    protected abstract List<T> parseResultSet(ResultSet rs) throws DAOException;

    /**
     * Sets insert query arguments in accordance with value object 'object' fields.
     * @param statement  statement to prepare
     * @param object object to use for operation
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws DAOException;

    /**
     * Sets update query arguments in accordance with value object 'object' fields.
     * @param statement  statement to prepare
     * @param object object to use for operation
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws DAOException;

    /**
     * Sets select query arguments in accordance with object 'object' fields.
     * @param statement  statement to prepare
     * @param object object to use for operation
     */
    protected abstract void prepareStatementForSelect(PreparedStatement statement, T object) throws DAOException;

    @Override
    public T persist(T object) throws DAOException {
        T persistInstance;
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        sql = getSelectQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForSelect(statement, object);
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
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, (Integer)key);
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
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForUpdate(statement, object);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<T> getAll() throws DAOException {
        List<T> list;
        String sql = getAllQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return list;
    }

    public AbstractPostgreDAO(Connection connection) {
        this.connection = connection;
    }
}
