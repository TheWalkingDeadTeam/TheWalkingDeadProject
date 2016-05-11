package ua.nc.dao.postgresql;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Interviewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rangar on 11.05.2016.
 */
public class PostgreUserInterviewerTableDAO {
    private Connection connection;

    public PostgreUserInterviewerTableDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String countQuerry = "SELECT COUNT(1) FROM system_user";
    private static final String getUsersQuery = "SELECT * FROM system_user";
    private static final String getInterviewersQuery = "SELECT system_user.system_user_id, system_user.name, system_user.surname, system_user.email, role.name as role, " +
            "            CASE WHEN system_user.system_user_id = interviewer_participation.system_user_id AND interviewer_participation.ces_id = ? THEN TRUE ELSE FALSE END as participation " +
            "            FROM system_user " +
            "            JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id " +
            "            JOIN role ON system_user_role.role_id = role.role_id " +
            "            LEFT JOIN interviewer_participation ON system_user.system_user_id = interviewer_participation.system_user_id " +
            "            WHERE role.name = 'ROLE_HR' OR role.name = 'ROLE_DEV' OR role.name = 'ROLE_BA' ";

    private static final String orderByQuery = " ORDER BY {0} ";
    private static final String pagingQuery = " LIMIT {0} OFFSET {1} ";

    private static final String getInterviewersCountQuery = "SELECT count(1)" +
            "            FROM system_user " +
            "            JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id " +
            "            JOIN role ON system_user_role.role_id = role.role_id " +
            "            LEFT JOIN interviewer_participation ON system_user.system_user_id = interviewer_participation.system_user_id " +
            "            WHERE role.name = 'ROLE_HR' OR role.name = 'ROLE_DEV' OR role.name = 'ROLE_BA' ";

    public Integer getInterviewersCount() throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(getInterviewersCountQuery)) {
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

    public List<Interviewer> getInterviewersTable(Integer cesId) throws DAOException {
        List<Interviewer> result;
        try (PreparedStatement statement = this.connection.prepareStatement(getInterviewersQuery)) {
            statement.setInt(1, cesId);
            return parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
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
