package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreApplicationDAO extends AbstractPostgreDAO<Application, Integer> implements ApplicationDAO {
    public  PostgreApplicationDAO(Connection connection){
        super(connection);
    }

    private class PersistApplication extends Application{
        public PersistApplication(int userID, int cesID) {
            super(userID, cesID);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    public static final String getApplicationByUserCES = "SELECT * FROM Application WHERE system_user_id = ? AND ces_id = ?";
    public static final String getAllCESApplicationsQuery = "SELECT * FROM Application WHERE ces_id = ?";

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
                application.setID(rs.getInt("application_id"));
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
            statement.setInt(2, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, Application object) throws DAOException {
        try {
            statement.setInt(1, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Application getApplicationByUserCES(Integer user_id, Integer ces_id) throws DAOException {
        Application result;
        try (PreparedStatement statement = connection.prepareStatement(getApplicationByUserCES)) {
            statement.setInt(1, user_id);
            statement.setInt(2, ces_id);
            result = parseResultSet(statement.executeQuery()).iterator().next();
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public List<Application> getAllCESApplications(Integer ces_id) throws DAOException {
        List<Application> result;
        try (PreparedStatement statement = connection.prepareStatement(getAllCESApplicationsQuery)) {
            statement.setInt(1, ces_id);
            result = parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    public Application create(Application object) throws DAOException {
        return persist(object);
    }
}