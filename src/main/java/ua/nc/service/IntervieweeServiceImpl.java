package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.IntervieweeDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Interviewee;

import java.sql.Connection;

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
            interviewee = intervieweeDAO.getById(id);
        } catch (DAOException ex){
            LOGGER.warn(ex);
        } finally {
            daoFactory.putConnection(connection);
        }
        return interviewee;
    }
}
