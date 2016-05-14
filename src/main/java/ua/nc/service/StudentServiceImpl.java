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


/**
 * Created by creed on 06.05.16.
 */
public class StudentServiceImpl implements StudentService {
    private final static Logger log = Logger.getLogger(StudentServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber) {
        Connection connection = daoFactory.getConnection();
        PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return applicationTableDAO.getApplicationsTable(ces.getId(), itemPerPage, pageNumber);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        }
        return null;
    }

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, Integer orderBy) {
        Connection connection = daoFactory.getConnection();
        PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            //ORDER BY
            return applicationTableDAO.getApplicationsTable(ces.getId(), itemPerPage, pageNumber, orderBy);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        }
        return null;
    }

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            //ORDER BY
            return applicationTableDAO.getApplicationsTable(ces.getId(), itemPerPage, pageNumber, pattern);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        }
        return null;
    }

    /**
     * @param action
     * @param studentsId list of Integer
     */
    @Override
    public void changeStatus(String action, List<Integer> studentsId) {
        if (Objects.equals(action, "reject")) {
            rejectStudents(studentsId);
        } else if (Objects.equals(action, "unreject")) {
            unrejectStudents(studentsId);
        } else {
            log.error(action + " action not supported");
        }
    }

//    /**
//     * @param studentsId list of Integer
//     */
//    @Override
//    public void activateStudents(List<Integer> studentsId) {
//        // StudentListDAO
//    }
//
//    /**
//     * @param studentsId list of Integer
//     */
//    @Override
//    public void deactivateStudents(List<Integer> studentsId) {
//        // StudentListDAO
//    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void rejectStudents(List<Integer> studentsId) {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            List<Application> applications = applicationDAO.getAllCESApplications(cesDAO.getCurrentCES().getId());
            for (Application application : applications) {
                application.setRejected(true);
                applicationDAO.update(application);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            daoFactory.putConnection(connection);
        }
        log.info("Sudent list rejected" + studentsId.toString());
    }

    @Override
    public void unrejectStudents(List<Integer> studentsId) {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            List<Application> applications = applicationDAO.getAllCESApplications(cesDAO.getCurrentCES().getId());
            for (Application application : applications) {
                application.setRejected(false);
                applicationDAO.update(application);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            daoFactory.putConnection(connection);
        }
        log.info("Sudent list unrejected" + studentsId.toString());
    }

    @Override
    public Integer getStudentsSize() {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
        return null;
    }
}
