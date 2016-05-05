package ua.nc.dao.factory;

import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.postgresql.PostgreDAOFactory;

import java.sql.Connection;


/**
 * Created by Pavel on 21.04.2016.
 */
public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(DataBaseType type) {
        switch (type) {
            case POSTGRESQL: {
                return new PostgreDAOFactory();
            }
            default: {
                return new PostgreDAOFactory();
            }
        }
    }

    public abstract Connection getConnection();

    public abstract void putConnection(Connection connection);

    public abstract UserDAO getUserDAO(Connection connection);

    public abstract RoleDAO getRoleDAO(Connection connection);

    public abstract MailDAO getMailDAO(Connection connection);

    public abstract ApplicationDAO getApplicationDAO(Connection connection);

    public abstract CESDAO getCESDAO(Connection connection);

    public abstract CESStatusDAO getCESStatusDAO(Connection connection);

    public abstract FeedbackDAO getFeedbackDAO(Connection connection);

    public abstract IntervieweeDAO getIntervieweeDAO(Connection connection);

    public abstract ReportTemplateDAO getReportTemplateDAO(Connection connection);

    public abstract FieldDAO getFieldDAO(Connection connection);

    public abstract FieldValueDAO getFieldValueDAO(Connection connection);

    public abstract ListValueDAO getListValueDAO(Connection connection);

    public abstract FieldTypeDAO getFieldTypeDAO(Connection connection);

    public abstract ListTypeDAO getListTypeDAO(Connection connection);
}
