package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationTableDAO;
import ua.nc.dao.postgresql.PostgreInterviewerTableDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.Interviewer;
import ua.nc.entity.profile.StudentData;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;


/**
 * Created by creed on 06.05.16.
 */
public class InterviewerServiceImpl implements InterviewerService {
    private final static Logger log = Logger.getLogger(InterviewerServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public List<Interviewer> getInterviewer(Integer itemPerPage, Integer pageNumber) {
        Connection connection = daoFactory.getConnection();
        PostgreInterviewerTableDAO userInterviewerTableDAO = new PostgreInterviewerTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return userInterviewerTableDAO.getInterviewersTable(ces.getId());
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
        if (Objects.equals(action, "activate")) {
            activateInterviewer(studentsId);
            log.info("Sudent list activate" + studentsId.toString());
        } else if (Objects.equals(action, "deactivate")) {
            deactivateInterviewer(studentsId);
            log.info("Sudent list deactivate" + studentsId.toString());
        } else if (Objects.equals(action, "reject")) {
            rejectInterviewer(studentsId);
            log.info("Sudent list reject" + studentsId.toString());
        } else {
            log.error(action + " action not supported");
        }
    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void activateInterviewer(List<Integer> studentsId) {
        // StudentListDAO
        //метод, который активирует список студентов
    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void deactivateInterviewer(List<Integer> studentsId) {
        // StudentListDAO
        //метод, которые деактивирует список студентов
    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void rejectInterviewer(List<Integer> studentsId) {
        /*Connection connection = daoFactory.getConnection();
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
        }*/
    }

    @Override
    public Integer getInterviewerSize() {
        Connection connection = daoFactory.getConnection();
        PostgreInterviewerTableDAO userInterviewerTableDAO = new PostgreInterviewerTableDAO(connection);
        try {
            return userInterviewerTableDAO.getInterviewersCount("");
        } catch (DAOException e) {
            log.error("Can`t get Interviewers size " + e.getCause());
        }
        return null;
    }
}
