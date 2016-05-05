package ua.nc.dao.factory;

import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.entity.CESStatus;
import ua.nc.entity.Feedback;
import ua.nc.entity.Interviewee;
import ua.nc.entity.ReportTemplate;
import ua.nc.entity.profile.FieldType;
import ua.nc.entity.profile.ListType;
import ua.nc.service.CESService;

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

    public abstract UserDAO getUserDAO(Connection connection);

    public abstract RoleDAO getRoleDAO(Connection connection);

    public abstract MailDAO getMailDAO(Connection connection);

    public abstract ApplicationDAO getApplicationDAO(Connection connection);

    public abstract CESDAO getCESDAO(Connection connection);

    public abstract GenericDAO<CESStatus, Integer> getCESStatusDAO(Connection connection);

    public abstract GenericDAO<Feedback, Integer> getFeedbackDAO(Connection connection);

    public abstract GenericDAO<Interviewee, Integer> getIntervieweeDAO(Connection connection);

    public abstract GenericDAO<ReportTemplate, Integer> getReportTemplateDAO(Connection connection);

    public abstract FieldDAO getFieldDAO(Connection connection);

    public abstract FieldValueDAO getFieldValueDAO(Connection connection);

    public abstract ListValueDAO getListValueDAO(Connection connection);

    public abstract GenericDAO<FieldType, Integer> getFieldTypeDAO(Connection connection);

    public abstract GenericDAO<ListType, Integer> getListTypeDAO(Connection connection);
}
