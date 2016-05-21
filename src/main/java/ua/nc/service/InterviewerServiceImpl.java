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
            return userInterviewerTableDAO.getInterviewersTable(ces.getId(), itemPerPage, pageNumber);
        } catch (DAOException e) {
            log.warn("Can't get interviews", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<Interviewer> getInterviewer(Integer itemPerPage, Integer pageNumber, String orderBy) {
        Connection connection = daoFactory.getConnection();
        PostgreInterviewerTableDAO userInterviewerTableDAO = new PostgreInterviewerTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return userInterviewerTableDAO.getInterviewersTable(ces.getId(), itemPerPage, pageNumber, orderBy);
        } catch (DAOException e) {
            log.warn("Can't get interviews", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<Interviewer> getInterviewer(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc) {
        Connection connection = daoFactory.getConnection();
        PostgreInterviewerTableDAO userInterviewerTableDAO = new PostgreInterviewerTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return userInterviewerTableDAO.getInterviewersTable(ces.getId(), itemPerPage, pageNumber, orderBy, asc);
        } catch (DAOException e) {
            log.warn("Can't get interviews", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<Interviewer> getInterviewer(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreInterviewerTableDAO userInterviewerTableDAO = new PostgreInterviewerTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return userInterviewerTableDAO.getInterviewersTable(ces.getId(), itemPerPage, pageNumber, orderBy, pattern);
        } catch (DAOException e) {
            log.warn("Can't get interviews", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    /**
     * @param action
     * @param studentsId list of Integer
     */
    @Override
    public void changeStatus(String action, List<Integer> studentsId) {
        if (Objects.equals(action, "subscribe")) {
            subscribeInterviewer(studentsId);
            log.info("Interviewer list subscribe" + studentsId.toString());
        }else if (Objects.equals(action, "unsubscribe")) {
            unsubscribeInterviewer(studentsId);
            log.info("Interviewer list unsubscribe" + studentsId.toString());
        } else {
            log.error(action + " action not supported");
        }
    }

//    /**
//     * @param studentsId list of Integer
//     */
//    @Override
//    public void activateInterviewer(List<Integer> studentsId) {
//        // StudentListDAO
//        //метод, который активирует список студентов
//    }
//
//    /**
//     * @param studentsId list of Integer
//     */
//    @Override
//    public void deactivateInterviewer(List<Integer> studentsId) {
//        // StudentListDAO
//        //метод, которые деактивирует список студентов
//    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void subscribeInterviewer(List<Integer> studentsId) {
        System.out.println("subscribe");
//        Connection connection = daoFactory.getConnection();
//        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
//        CESDAO cesDAO = daoFactory.getCESDAO(connection);
//        try {
//            List<Application> applications = applicationDAO.getAllCESApplications(cesDAO.getCurrentCES().getId());
//            for (Application application : applications) {
//                application.setRejected(true);
//                applicationDAO.update(application);
//            }
//        } catch (DAOException e) {
//            e.printStackTrace();
//        } finally {
//            daoFactory.putConnection(connection);
//        }
    }

    @Override
    public void unsubscribeInterviewer(List<Integer> studentsId) {
        System.out.println("unsubscribe");
//        Connection connection = daoFactory.getConnection();
//        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
//        CESDAO cesDAO = daoFactory.getCESDAO(connection);
//        try {
//            List<Application> applications = applicationDAO.getAllCESApplications(cesDAO.getCurrentCES().getId());
//            for (Application application : applications) {
//                application.setRejected(true);
//                applicationDAO.update(application);
//            }
//        } catch (DAOException e) {
//            e.printStackTrace();
//        } finally {
//            daoFactory.putConnection(connection);
//        }
    }

    @Override
    public Integer getInterviewerSize(String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreInterviewerTableDAO userInterviewerTableDAO = new PostgreInterviewerTableDAO(connection);
        try {
            return userInterviewerTableDAO.getInterviewersCount(pattern);
        } catch (DAOException e) {
            log.error("Can`t get Interviewers size " + e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }
}
