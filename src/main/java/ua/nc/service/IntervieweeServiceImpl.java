package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.IntervieweeDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreIntervieweeTableDAO;
import ua.nc.entity.CES;
import ua.nc.entity.Interviewee;
import ua.nc.entity.IntervieweeRow;
import ua.nc.entity.User;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Hlib on 10.05.2016.
 */
public class IntervieweeServiceImpl implements IntervieweeService {
    private final static Logger LOGGER = Logger.getLogger(IntervieweeServiceImpl.class);
    private final static DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public Interviewee getInterviewee(int id) {
        Connection connection = DAO_FACTORY.getConnection();
        Interviewee interviewee = null;
        try{
            IntervieweeDAO intervieweeDAO = DAO_FACTORY.getIntervieweeDAO(connection);

            interviewee = intervieweeDAO.read(id);
        } catch (DAOException ex){
            LOGGER.warn(ex);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return interviewee;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber) {
        Connection connection = DAO_FACTORY.getConnection();

        try {
            PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy) {
        Connection connection = DAO_FACTORY.getConnection();
        PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber,orderBy);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc) {
        Connection connection = DAO_FACTORY.getConnection();

        try {
            PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber, orderBy, asc);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern) {
        Connection connection = DAO_FACTORY.getConnection();

        try {
            PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber, orderBy, pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public void changeStatus(String action, List<Integer> studentsId) {

    }

    @Override
    public void subscribeInterviewee(List<Integer> studentsId) {

    }

    @Override
    public void unsubscribeInterviewee(List<Integer> studentsId) {

    }

    @Override
    public Integer getIntervieweeSize(String pattern) {
        Connection connection = DAO_FACTORY.getConnection();

        try {
            PostgreIntervieweeTableDAO userIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
            CESServiceImpl cesService = new CESServiceImpl();
            CES ces = cesService.getCurrentCES();
            return userIntervieweeTableDAO.getIntervieweeCount(ces.getId(), pattern);
        } catch (DAOException e) {
            LOGGER.error("Can`t get Interviewee size " + e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public void createInteviewees(List<User> studentGroup, Map<Integer, Integer> applicationList, Date interviewDate) {
        Connection connection = DAO_FACTORY.getConnection();
        IntervieweeDAO intDAO = DAO_FACTORY.getIntervieweeDAO(connection);
        for (User user : studentGroup) {
            int appId = applicationList.remove(user.getId());
            try {
                intDAO.read(appId);
            } catch (DAOException ex) {
                if (ex.getMessage().equals("Record with PK = " + appId + " not found.")) {
                    try {
                        intDAO.create(new Interviewee(appId, interviewDate));
                    } catch (DAOException e) {
                        DAO_FACTORY.putConnection(connection);
                        LOGGER.error(e.getCause());
                    }
                }
            }
        }
        DAO_FACTORY.putConnection(connection);
    }
}
