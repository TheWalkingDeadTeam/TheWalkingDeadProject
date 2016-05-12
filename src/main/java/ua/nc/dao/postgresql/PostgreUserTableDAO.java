package ua.nc.dao.postgresql;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.UserRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Rangar on 12.05.2016.
 */
public class PostgreUserTableDAO {
    private Connection connection;

    public PostgreUserTableDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String[] SET_VALUES = new String[] { "system_user_id", "name", "surname", "email", "status"};
    private static final Set<String> FIELDS = new HashSet<>(Arrays.asList(SET_VALUES));

    private static final String COUNT_USERS = "SELECT COUNT(1) FROM system_user " +
            "JOIN system_user_status " +
            "ON  system_user.system_user_status_id = system_user_status.system_user_status_id " +
            "WHERE (system_user.name LIKE ? OR system_user.surname LIKE ? OR system_user.email LIKE ? OR system_user_status.name LIKE ?) ";

    private static final String GET_USERS_QUERRY = "SELECT system_user.system_user_id, system_user.name, system_user.surname, " +
            "system_user.email, system_user_status.name as status,  " +
            "array_agg(role.name) as role " +
            "FROM system_user  " +
            "JOIN system_user_status  " +
            "ON  system_user.system_user_status_id = system_user_status.system_user_status_id " +
            "JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id  " +
            "JOIN role ON system_user_role.role_id = role.role_id " +
            "WHERE (system_user.name LIKE ? OR system_user.surname LIKE ? OR system_user.email LIKE ? OR system_user_status.name LIKE ?)  " +
            "GROUP BY system_user.system_user_id, system_user_status.name " +
            "ORDER BY {0} {1} " +
            "LIMIT ? OFFSET ? ";

    public Integer getUsersCount(String pattern) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(COUNT_USERS)) {
            statement.setString(1, "%" + pattern + "%");
            statement.setString(2, "%" + pattern + "%");
            statement.setString(3, "%" + pattern + "%");
            statement.setString(4, "%" + pattern + "%");
            return getCount(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private Integer getCount(ResultSet rs) throws SQLException {
        Integer result = 0;
        while (rs.next()) {
            result = rs.getInt("count");
        }
        return result;
    }

    private String buildQuery(String orderBy, Boolean asc) throws DAOException {
        if (FIELDS.contains(orderBy)){
            String order = asc ? "ASC" : "DESC";
            return MessageFormat.format(GET_USERS_QUERRY, orderBy, order);
        } else {
            throw new DAOException("Nice try, but injection isn't allowed");
        }
    }

    public List<UserRow> getUsersTable() throws DAOException {
        return  getUsers(null, null, "system_user_id", "", true);
    }

    public List<UserRow> getUsersTable(Integer limit, Integer offset) throws DAOException {
        return getUsers(limit, offset, "system_user_id", "", true);
    }

    public List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy) throws DAOException {
        return getUsers(limit, offset, orderBy, "", true);
    }

    public List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy, Boolean asc) throws DAOException {
        return getUsers(limit, offset, orderBy, "", asc);
    }

    public List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy ,String pattern) throws DAOException {
        return getUsers(limit, offset, orderBy, pattern, true);
    }

    public List<UserRow> getUsersTable(Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException {
        return getUsers(limit, offset, orderBy, pattern, asc);
    }

    private List<UserRow> getUsers(Integer limit, Integer offset, String orderBy , String pattern, Boolean asc) throws DAOException{
        String fullQuery = buildQuery(orderBy, asc);
        try (PreparedStatement statement = this.connection.prepareStatement(fullQuery)) {
            statement.setString(1, "%" + pattern + "%");
            statement.setString(2, "%" + pattern + "%");
            statement.setString(3, "%" + pattern + "%");
            statement.setString(4, "%" + pattern + "%");
            statement.setObject(5, limit);
            statement.setObject(6, offset);
            return parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private List<UserRow> parseResultSet(ResultSet rs) throws SQLException {
        List<UserRow> result = new LinkedList<>();
        while (rs.next()){
            UserRow interviewer = new UserRow();
            interviewer.id = rs.getInt("system_user_id");
            interviewer.name = rs.getString("name");
            interviewer.surname = rs.getString("surname");
            interviewer.email = rs.getString("email");
            interviewer.role = rs.getString("role");
            interviewer.status = rs.getString("status");
            result.add(interviewer);
        }
        return result;
    }
}
