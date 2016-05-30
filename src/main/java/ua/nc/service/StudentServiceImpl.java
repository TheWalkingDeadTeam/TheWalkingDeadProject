package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationTableDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.profile.StudentData;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

public class StudentServiceImpl implements StudentService {
    private final static Logger log = Logger.getLogger(StudentServiceImpl.class);
    private final DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber) {
        Connection connection = DAO_FACTORY.getConnection();
        try {
            PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            StudentData studentData = applicationTableDAO.getApplicationsTable(ces.getId(), itemPerPage, pageNumber);
            return studentData;
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, String pattern) {
        Connection connection = DAO_FACTORY.getConnection();
        try {
            PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return applicationTableDAO.getApplicationsTable(ces.getId(), itemPerPage, pageNumber, pattern);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, Integer sortType, Boolean asc) {
        Connection connection = DAO_FACTORY.getConnection();
        try {
            PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return applicationTableDAO.getApplicationsTable(ces.getId(), itemPerPage, pageNumber, sortType, asc);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public Integer getSize(String pattern) {
        Connection connection = DAO_FACTORY.getConnection();
        try {
            PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return applicationTableDAO.getApplicationsCount(ces.getId(), pattern);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public void changeStatus(String action, List<Integer> studentsId) {
        if (Objects.equals(action, "reject")) {
            rejectStudents(studentsId);
        } else if (Objects.equals(action, "unreject")) {
            acceptStudents(studentsId);
        } else {
            log.error(action + " action not supported");
        }
    }
     @Override
    public void rejectStudents(List<Integer> studentsId) {
        changeApplicationStatus(studentsId, true);
        log.info("List of applications was rejected " + studentsId);
    }

    @Override
    public void acceptStudents(List<Integer> studentsId) {
        changeApplicationStatus(studentsId, false);
        log.info("List of applications was accepted " + studentsId);
    }

    private void changeApplicationStatus(List<Integer> studentsId, Boolean status) {
        Connection connection = DAO_FACTORY.getConnection();
        ApplicationDAO applicationDAO = DAO_FACTORY.getApplicationDAO(connection);
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        try {
            Integer cesId = cesDAO.getCurrentCES().getId();
            List<Application> applications = applicationDAO.getApplicationsByCesIdUserId(cesId, studentsId);
            for (Application application : applications) {
                application.setRejected(status);
                applicationDAO.update(application);
            }
        } catch (DAOException e) {
            log.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

}
