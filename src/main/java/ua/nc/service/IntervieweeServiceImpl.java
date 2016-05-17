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

import java.sql.Connection;
import java.util.List;

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
        }
        return null;
    }
}
