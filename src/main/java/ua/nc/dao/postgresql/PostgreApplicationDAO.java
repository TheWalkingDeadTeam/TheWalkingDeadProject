package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Application;
import ua.nc.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreApplicationDAO extends AbstractPostgreDAO<Application, Integer> implements ApplicationDAO {
    private static final String GET_APPLICATION_BY_USER_CES = "SELECT * FROM Application WHERE system_user_id = ? AND ces_id = ?";
    private static final String GET_ALL_CES_APPLICATIONS_QUERY = "SELECT * FROM Application WHERE ces_id = ?";

    private static final String GET_APPLICATIONS_BY_CES_ID_USER_ID = "SELECT application.* " +
            "FROM application  " +
            "JOIN system_user ON application.system_user_id = system_user.system_user_id  " +
            "WHERE application.ces_id = ? AND system_user.system_user_id = ANY (?) ";

    private static final String GET_ALL_ACCEPTED_APPLICATIONS_QUERY = "SELECT * FROM application WHERE ces_id = ? AND rejected = FALSE";

    public PostgreApplicationDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM Application WHERE application_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO Application (system_user_id, ces_id) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE Application SET rejected = ? WHERE Application_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM Application";
    }

    @Override
    protected List<Application> parseResultSet(ResultSet rs) throws DAOException {
        List<Application> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistApplication application = new PersistApplication(rs.getInt("system_user_id"), rs.getInt("ces_id"));
                application.setId(rs.getInt("application_id"));
                application.setRejected(rs.getBoolean("rejected"));
                result.add(application);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Application object) throws DAOException {
        try {
            statement.setInt(1, object.getUserID());
            statement.setInt(2, object.getCesID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Application object) throws DAOException {
        try {
            statement.setBoolean(1, object.getRejected());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Application getApplicationByUserCES(Integer userId, Integer cesId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(GET_APPLICATION_BY_USER_CES)) {
            statement.setInt(1, userId);
            statement.setInt(2, cesId);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() ) {
                return null;
            } else {
                return parseResultSet(rs).iterator().next();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public List<Application> getApplicationsByCesIdUserId(Integer cesId, List<Integer> userIds) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(GET_APPLICATIONS_BY_CES_ID_USER_ID)) {
            statement.setInt(1, cesId);
            statement.setArray(2, connection.createArrayOf("integer", userIds.toArray()));
            return parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Map<Integer, Integer> getAllAcceptedApplications(Integer cesId) throws DAOException {
        return getSomeApplications(GET_ALL_ACCEPTED_APPLICATIONS_QUERY, cesId);
    }

    private Map<Integer, Integer> getSomeApplications(String query, Integer cesId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cesId);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() ) {
                return null;
            } else {
                return getResultMap(rs);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private Map<Integer, Integer> getResultMap(ResultSet rs) throws DAOException {
        Map<Integer, Integer> result = new HashMap<>();
        try {
            while (rs.next()) {
                result.put(rs.getInt("system_user_id"), rs.getInt("application_id"));
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public Application create(Application object) throws DAOException {
        return persist(object);
    }

    private class PersistApplication extends Application {
        public PersistApplication(int userID, int cesID) {
            super(userID, cesID);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }
}
