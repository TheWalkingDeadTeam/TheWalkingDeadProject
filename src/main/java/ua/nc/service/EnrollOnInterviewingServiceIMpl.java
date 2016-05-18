package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.CESDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Yaroslav on 09.05.2016.
 */
class EnrollOnInterviewingServiceImpl implements CESService {
    private final static Logger LOGGER = Logger.getLogger(CESServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);


    @Override
    public CES getCurrentCES() {
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        CES ces = null;
        try {
            ces = cesdao.getCurrentCES();
            LOGGER.info("Successfully get current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't get current CES", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return ces;
    }

//    @Override
//    public void enrollAsStudent(Integer userId, Integer cesId) throws DAOException {
//
//    }

    @Override
    public void enrollAsInterviewer(Integer userId, Integer cesId) throws DAOException {

    }

    @Override
    public void enrollAsStudent(Integer userId, Integer cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO CESDAO = new PostgreCESDAO(connection);
        try {
            CESDAO.addInterviewerForCurrentCES(cesId,userId);
            LOGGER.info("Successfully enrolled to current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't enroll to current CES");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }

    }

    /**
     * Plan current interview schedule and send notifications to all the participants.
     *
     * @param interviewerMail       email template to send to all the interviewers.
     * @param interviewerParameters parameters to set in interviewer template to personalize the emails.
     * @param studentMail           email template to send to all the students.
     * @param studentParameters     parameters to set in student template to personalize the emails.
     * @return schedule of the current CES interviews.
     * @throws DAOException missing data about current course enrolment session.
     */
    @Override
    public List<Date> planSchedule(Mail interviewerMail, Map<String, String> interviewerParameters, Mail studentMail, Map<String, String> studentParameters) throws DAOException {
        return null;
    }
}
