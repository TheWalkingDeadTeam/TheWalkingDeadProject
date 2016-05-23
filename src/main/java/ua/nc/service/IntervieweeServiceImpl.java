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
    private final static DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public Interviewee getInterviewee(int id) {
        Connection connection = daoFactory.getConnection();
        IntervieweeDAO intervieweeDAO = daoFactory.getIntervieweeDAO(connection);
        Interviewee interviewee = null;
        try{
            interviewee = intervieweeDAO.read(id);
        } catch (DAOException ex){
            LOGGER.warn(ex);
        } finally {
            daoFactory.putConnection(connection);
        }
        return interviewee;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber) {
        Connection connection = daoFactory.getConnection();
        PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy) {
        Connection connection = daoFactory.getConnection();
        PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber,orderBy);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc) {
        Connection connection = daoFactory.getConnection();
        PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber, orderBy, asc);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreIntervieweeTableDAO postgreIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return postgreIntervieweeTableDAO.getIntervieweeTable(ces.getId(), itemPerPage, pageNumber, orderBy, pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get interviewee", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
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
        Connection connection = daoFactory.getConnection();
        PostgreIntervieweeTableDAO userIntervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            return userIntervieweeTableDAO.getIntervieweeCount(ces.getId(), pattern);
        } catch (DAOException e) {
            LOGGER.error("Can`t get Interviewee size " + e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public void createInteviewees(List<User> studentGroup, Map<Integer, Integer> applicationList, Date interviewDate) {
        Connection connection = daoFactory.getConnection();
        IntervieweeDAO intDAO = daoFactory.getIntervieweeDAO(connection);
        for (User user : studentGroup) {
            int appId = applicationList.remove(user.getId());
            try {
                intDAO.read(appId);
            } catch (DAOException ex) {
                if (ex.getMessage().equals("Record with PK = " + appId + " not found.")) {
                    try {
                        intDAO.create(new Interviewee(appId, interviewDate));
                    } catch (DAOException e) {
                        daoFactory.putConnection(connection);
                        LOGGER.error(e.getCause());
                    }
                }
            }
        }
        daoFactory.putConnection(connection);
    }
}
