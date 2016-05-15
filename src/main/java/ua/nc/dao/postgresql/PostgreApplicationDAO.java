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
import java.util.List;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreApplicationDAO extends AbstractPostgreDAO<Application, Integer> implements ApplicationDAO {
    public static final String GET_APPLICATION_BY_USER_CES = "SELECT * FROM Application WHERE system_user_id = ? AND ces_id = ?";
    public static final String GET_ALL_CES_APPLICATIONS_QUERY = "SELECT * FROM Application WHERE ces_id = ?";
    public static final String GET_ALL_USERS_FOR_CURRENT_CES = "Select u.* from system_user u JOIN application a " +
            "ON u.system_user_id = a.system_user_id WHERE ces_id = " +
            "(SELECT ces.ces_id from course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id AND stat.name = 'Active')";

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
    public Application getApplicationByUserCES(Integer user_id, Integer ces_id) throws DAOException {
        Application result;
        try (PreparedStatement statement = connection.prepareStatement(GET_APPLICATION_BY_USER_CES)) {
            statement.setInt(1, user_id);
            statement.setInt(2, ces_id);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() ) {
                result = null;
            } else {
                result = parseResultSet(rs).iterator().next();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public List<Application> getAllCESApplications(Integer ces_id) throws DAOException {
        List<Application> result;
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_CES_APPLICATIONS_QUERY)) {
            statement.setInt(1, ces_id);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst() ) {
                result = null;
            } else {
                result = parseResultSet(rs);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public List<User> getStudentsForCurrentCES() throws DAOException {
        return null;
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
