package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.InterviewerParticipationDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Connection;

/**
 * Created by Max Morozov on 07.05.2016.
 */
public class CESServiceImpl implements CESService {

    private final static Logger LOGGER = Logger.getLogger(CESServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private CESDAO cesDAO = daoFactory.getCESDAO(daoFactory.getConnection());
    private InterviewerParticipationDAO interviewerParticipationDAO =
            daoFactory.getInterviewerParticipationDAO(daoFactory.getConnection());
    private ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(daoFactory.getConnection());
    private static final int MINUTES_PER_HOUR = 60;
    private static final int INTERVIEWERS_PER_STUDENT = 2;
    private static final int MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

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

    @Override
    public void enroll(UserDetailsImpl userDetails) {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = new PostgreApplicationDAO(connection);
        Application application = new Application();
        application.setUserID(userId);
        application.setCesID(currentCESId);
        try {
            applicationDAO.create(application);
            LOGGER.info("Successfully enrolled to current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't enroll to current CES");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public List<Date> planSchedule(Mail interviewerMail, Map<String, String> interviewerParameters,
                                   Mail studentMail, Map<String, String> studentParameters) throws DAOException {
        //get parameters
        CES ces = cesDAO.getCurrentCES();
        Date startDate = ces.getStartInterviewingDate();
        int hoursPerDay = ces.getInterviewTimeForDay();
        int timePerStudent = ces.getInterviewTimeForPerson();
        List<User> interviewersList = interviewerParticipationDAO.getInterviewersForCurrentCES();
        List<User> studentsList = applicationDAO.getStudentsForCurrentCES();

        //calculate end date
        int studentsAmount = studentsList.size();
        int interviewersAmount = interviewersList.size();
        Date endDate = new Date(startDate.getTime() + studentsAmount / (MINUTES_PER_HOUR / timePerStudent * hoursPerDay)
                / interviewersAmount * INTERVIEWERS_PER_STUDENT);
        ces.setEndInterviewingDate(endDate);

        //make interview dates list
        List<Date> interviewDates = new ArrayList<>();
        interviewDates.add(startDate);
        long currentTime = startDate.getTime();
        while (currentTime < endDate.getTime()) {
            currentTime += MILLIS_PER_DAY;
            interviewDates.add(startDate);
        }

        return interviewDates;
    }

}
