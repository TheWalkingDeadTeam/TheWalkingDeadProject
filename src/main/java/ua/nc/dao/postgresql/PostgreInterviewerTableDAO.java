package ua.nc.dao.postgresql;

import ua.nc.dao.InterviewerTableDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Interviewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Rangar on 11.05.2016.
 */
public class PostgreInterviewerTableDAO implements InterviewerTableDAO {

    private static final String[] SET_VALUES = new String[] { "system_user_id", "name", "surname", "email", "role"};
    private static final Set<String> FIELDS = new HashSet<>(Arrays.asList(SET_VALUES));

    private Connection connection;

    public PostgreInterviewerTableDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String GET_INTERVIEWERS_QUERY = "SELECT system_user.system_user_id, system_user.name, system_user.surname, system_user.email, role.name as role, " +
            "            CASE WHEN system_user.system_user_id = interviewer_participation.system_user_id AND interviewer_participation.ces_id = ? THEN TRUE ELSE FALSE END as participation " +
            "            FROM system_user " +
            "            JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id " +
            "            JOIN role ON system_user_role.role_id = role.role_id " +
            "            LEFT JOIN interviewer_participation ON system_user.system_user_id = interviewer_participation.system_user_id " +
            "            WHERE (role.role_id = 3 OR role.role_id = 4 OR role.role_id = 5) " +
            "            AND (system_user.name LIKE ? OR system_user.surname LIKE ? OR system_user.email LIKE ? OR role.name LIKE ?) " +
            "            ORDER BY {0} {1} " +
            "            LIMIT ? OFFSET ? ";

    private static final String GET_INTERVIEWERS_COUNT_QUERY = "SELECT count(*)" +
            "            FROM system_user " +
            "            JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id " +
            "            JOIN role ON system_user_role.role_id = role.role_id " +
            "            LEFT JOIN interviewer_participation ON system_user.system_user_id = interviewer_participation.system_user_id " +
            "            WHERE (role.role_id = 3 OR role.role_id = 4 OR role.role_id = 5) " +
            "            AND (system_user.name LIKE ? OR system_user.surname LIKE ? OR system_user.email LIKE ? OR role.name LIKE ?) ";

    public Integer getInterviewersCount(String pattern) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(GET_INTERVIEWERS_COUNT_QUERY)) {
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
        rs.next();
        return rs.getInt("count");
    }

    public List<Interviewer> getInterviewersTable(Integer cesId) throws DAOException {
        return  getInterviewers(cesId, null, null, "system_user_id", "", true);
    }

    public List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset) throws DAOException {
        return getInterviewers(cesId, limit, offset, "system_user_id", "", true);
    }

    public List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy) throws DAOException {
        return getInterviewers(cesId, limit, offset, orderBy, "", true);
    }

    public List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy, Boolean asc) throws DAOException {
        return getInterviewers(cesId, limit, offset, orderBy, "", asc);
    }

    public List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern) throws DAOException {
        return getInterviewers(cesId, limit, offset, orderBy, pattern, true);
    }

    public List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException {
        return getInterviewers(cesId, limit, offset, orderBy, pattern, asc);
    }

    private List<Interviewer> getInterviewers(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException{
        String fullQuery = buildQuery(orderBy, asc);
        try (PreparedStatement statement = this.connection.prepareStatement(fullQuery)) {
            statement.setInt(1, cesId);
            statement.setString(2, "%" + pattern + "%");
            statement.setString(3, "%" + pattern + "%");
            statement.setString(4, "%" + pattern + "%");
            statement.setString(5, "%" + pattern + "%");
            statement.setObject(6, limit);
            statement.setObject(7, offset);
            return parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private String buildQuery(String orderBy, Boolean asc) throws DAOException {
        if (FIELDS.contains(orderBy)){
            String order = asc ? "ASC" : "DESC";
            return MessageFormat.format(GET_INTERVIEWERS_QUERY, orderBy, order);
        } else {
            throw new DAOException("Nice try, but injection isn't allowed");
        }
    }

    private List<Interviewer> parseResultSet(ResultSet rs) throws SQLException {
        List<Interviewer> result = new LinkedList<>();
        while (rs.next()){
            Interviewer interviewer = new Interviewer();
            interviewer.id = rs.getInt("system_user_id");
            interviewer.name = rs.getString("name");
            interviewer.surname = rs.getString("surname");
            interviewer.email = rs.getString("email");
            interviewer.role = rs.getString("role");
            interviewer.participation = rs.getBoolean("participation");
            result.add(interviewer);
        }
        return result;
    }
}
