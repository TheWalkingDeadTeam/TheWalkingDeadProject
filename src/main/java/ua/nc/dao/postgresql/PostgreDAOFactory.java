package ua.nc.dao.postgresql;

import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.dao.postgresql.profile.*;
import ua.nc.entity.CESStatus;
import ua.nc.entity.Feedback;
import ua.nc.entity.Interviewee;
import ua.nc.entity.ReportTemplate;
import ua.nc.entity.profile.FieldType;
import ua.nc.entity.profile.ListType;

import java.sql.Connection;

/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreDAOFactory extends DAOFactory {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool(DataBaseType.POSTGRESQL);


    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
        } catch (Exception e) {
            System.out.println("GET CONNECTION");
        }
        return connection;
    }

    @Override
    public UserDAO getUserDAO(Connection connection) {
        return new PostgreUserDAO(connection);
    }

    @Override
    public RoleDAO getRoleDAO(Connection connection) {
        return new PostgreRoleDAO(connection);
    }

    @Override
    public MailDAO getMailDAO(Connection connection) {
        return new PostgreMailDAO(connection);
    }

    @Override
    public ApplicationDAO getApplicationDAO(Connection connection) {
        return new PostgreApplicationDAO(connection);
    }

    @Override
    public CESDAO getCESDAO(Connection connection) {
        return new PostgreCESDAO(connection);
    }

    @Override
    public CESStatusDAO getCESStatusDAO(Connection connection) {
        return new PostgreCESStatusDAO(connection);
    }

    @Override
    public FeedbackDAO getFeedbackDAO(Connection connection) {
        return new PostgreFeedbackDAO(connection);
    }

    @Override
    public IntervieweeDAO getIntervieweeDAO(Connection connection) {
        return new PostgreIntervieweeDAO(connection);
    }

    @Override
    public ReportTemplateDAO getReportTemplateDAO(Connection connection) {
        return new PostgreReportTemplateDAO(connection);
    }

    @Override
    public FieldDAO getFieldDAO(Connection connection) {
        return new PostgreFieldDAO(connection);
    }

    @Override
    public FieldValueDAO getFieldValueDAO(Connection connection) {
        return new PostgreFieldValueDAO(connection);
    }

    @Override
    public ListValueDAO getListValueDAO(Connection connection) {
        return new PostgreListValueDAO(connection);
    }

    @Override
    public FieldTypeDAO getFieldTypeDAO(Connection connection) {
        return new PostgreFieldTypeDAO(connection);
    }

    @Override
    public ListTypeDAO getListTypeDAO(Connection connection) {
        return new PostgreListTypeDAO(connection);
    }
}
