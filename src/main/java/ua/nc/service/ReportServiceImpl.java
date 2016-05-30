package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationTableDAO;
import ua.nc.dao.ReportTemplateDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationTableDAO;
import ua.nc.dao.postgresql.PostgreReportTemplateDAO;
import ua.nc.entity.ReportTemplate;
import ua.nc.validator.ReportExecuteValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Pavel on 11.05.2016.
 */
public class ReportServiceImpl implements ReportService {
    private static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);
    private final DAOFactory daoFactory  = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private final Validator reportExecuteValidator = new ReportExecuteValidator();


    @Override
    public List<ReportTemplate> getReports() {
        Connection connection = daoFactory.getConnection();
        ReportTemplateDAO reportTemplateDAO = new PostgreReportTemplateDAO(connection);
        List<ReportTemplate> reports = new ArrayList<>();
        try {
            reports = reportTemplateDAO.getAll();
        } catch (DAOException e) {
            LOGGER.warn("Can't retrieve reports");
        } finally {
            daoFactory.putConnection(connection);
        }
        return reports;
    }

    @Override
    public ReportTemplate getReportById(Integer id) {
        Connection connection = daoFactory.getConnection();
        ReportTemplateDAO reportTemplateDAO = new PostgreReportTemplateDAO(connection);
        ReportTemplate report = null;
        try {
            report = reportTemplateDAO.read(id);
        } catch (DAOException e) {
            LOGGER.warn("Can't retrieve report " + id);
        } finally {
            daoFactory.putConnection(connection);
        }
        return report;
    }

    @Override
    public void createReport(ReportTemplate report) {
        Connection connection = daoFactory.getConnection();
        ReportTemplateDAO reportTemplateDAO = new PostgreReportTemplateDAO(connection);
        try {
            reportTemplateDAO.create(report);
        } catch (DAOException e) {
            LOGGER.warn("Can't create report " + report.getName() + " Query :" + report.getQuery());
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void updateReport(ReportTemplate report) {
        Connection connection = daoFactory.getConnection();
        ReportTemplateDAO reportTemplateDAO = new PostgreReportTemplateDAO(connection);
        try {
            reportTemplateDAO.update(report);
        } catch (DAOException e) {
            LOGGER.warn("Can't update report " + report.getId());
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void deleteReport(ReportTemplate report) {
        Connection connection = daoFactory.getConnection();
        ReportTemplateDAO reportTemplateDAO = new PostgreReportTemplateDAO(connection);
        try {
            reportTemplateDAO.delete(report);
        } catch (DAOException e) {
            LOGGER.warn("Can't delete report " + report.getId());
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public List<Map<String, Object>> getReportRows(ReportTemplate report) throws DAOException {
        Connection connection = daoFactory.getConnection();
        ReportTemplateDAO reportTemplateDAO = new PostgreReportTemplateDAO(connection);
        List<Map<String, Object>> reportRows = null;
        Set<ValidationError> errors = reportExecuteValidator.validate(report.getQuery());
        if (errors.isEmpty()) {
            try {
                reportRows = reportTemplateDAO.execute(report);
                LOGGER.info("Report " + report.getName() + " successfully execute");
            } catch (DAOException e) {
                LOGGER.warn("Can't execute report query " + report.getId());
                throw new DAOException(e);
            } finally {
                daoFactory.putConnection(connection);
            }
        } else {
            LOGGER.warn("Report "+ report.getName() +" query has errors");
            throw new DAOException("Report query has errors");
        }
        return  reportRows;
    }

    @Override
    public ReportTemplate getUserProfileQueryById (Integer id) {
        Connection connection = daoFactory.getConnection();
        ReportTemplate reportTemplate = new ReportTemplate();
        ApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
        try {
            reportTemplate.setName("Profile Report");
            reportTemplate.setQuery(applicationTableDAO.getFullQuery(id));
        } catch (DAOException e) {
            LOGGER.warn("Cant get applicatio user profile query for ces id " + id, e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return reportTemplate;
    }
}
