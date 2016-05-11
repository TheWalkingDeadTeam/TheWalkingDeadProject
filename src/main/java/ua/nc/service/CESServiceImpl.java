package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.CESStatusDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.dao.postgresql.PostgreUserDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pavel on 06.05.2016.
 */
public class CESServiceImpl implements CESService {

    private final static Logger LOGGER = Logger.getLogger(CESServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private ThreadPoolTaskScheduler scheduler;
    private static final int POOL_SIZE = 1;

    private static final int MINUTES_PER_HOUR = 60;
    private static final int INTERVIEWERS_PER_STUDENT = 2;
    private static final int MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

    @Override
    public List<CES> getAllCES() {
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        List<CES> allCES = new ArrayList<>();
        try{
            allCES = cesdao.getAll();
            LOGGER.info("Successfully get all CES history");
        }  catch (DAOException e){
            LOGGER.warn("Can't get all CES history", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return allCES;
    }


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
    public void enrollAsStudent(Integer userId, Integer currentCESId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = new PostgreApplicationDAO(connection);
        Application application = new Application();
        application.setUserID(userId);
        application.setCesID(currentCESId);
        try {
            applicationDAO.create(application);
            LOGGER.info("Successfully enrolled to current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't enrollAsStudent to current CES", e.getCause());
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    public CESServiceImpl() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);//
        scheduler.initialize();//
    }

    @Override
    public void enrollAsInterviewer(Integer userId, Integer cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        try {
            cesdao.addInterviewerForCurrentCES(cesId, userId);
            LOGGER.info("Successfully enrolled to current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't enrollAsStudent to current CES");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public List<Date> planSchedule() throws DAOException {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = new PostgreUserDAO(connection);
        CESDAO cesDAO = new PostgreCESDAO(connection);
        //get parameters
        CES ces = cesDAO.getCurrentCES();
        Date startDate = ces.getStartInterviewingDate();
        int hoursPerDay = ces.getInterviewTimeForDay();
        int timePerStudent = ces.getInterviewTimeForPerson();


        //Changed By Pasha
        Set<User> interviewersList = userDAO.getInterviewersForCurrentCES();
        Set<User> studentsList = userDAO.getStudentsForCurrentCES();

        //calculate end date
        int studentsAmount = studentsList.size();
        int interviewersAmount = interviewersList.size();
        Date endDate = new Date(startDate.getTime() + (studentsAmount / (MINUTES_PER_HOUR * hoursPerDay / timePerStudent)
                * INTERVIEWERS_PER_STUDENT / interviewersAmount) * MILLIS_PER_DAY);
        ces.setEndInterviewingDate(endDate);

        //make interview dates list
        List<Date> interviewDates = new ArrayList<>();
        interviewDates.add(startDate);
        long currentTime = startDate.getTime();
        while (currentTime < endDate.getTime()) {
            currentTime += MILLIS_PER_DAY;
            interviewDates.add(new Date(currentTime));
        }
        return interviewDates;
    }
}


    @Override
    public CES getCES() throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        if (cesDAO.getCurrentCES() != null){
            return cesDAO.getCurrentCES();
        } else if(cesDAO.getPendingCES() != null){
            return cesDAO.getPendingCES();
        } else if (cesDAO.getCurrentInterviewBegunCES() != null){
            return cesDAO.getCurrentInterviewBegunCES();
        }
        return null;
    }

    @Override
    public void setCES(CES ces) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        CES cesFromDb;
        try {
            if (cesDAO.getCurrentInterviewBegunCES() != null){
                cesFromDb = cesDAO.getCurrentCES();
                cesFromDb.setQuota(ces.getQuota());
                cesDAO.update(cesFromDb);
                LOGGER.info("Current CES was updated");
            }
            if (cesDAO.getCurrentCES() != null) {
                cesFromDb = cesDAO.getCurrentCES();
                cesFromDb.setStartInterviewingDate(ces.getStartInterviewingDate());
                cesFromDb.setEndInterviewingDate(ces.getEndInterviewingDate());
                cesFromDb.setInterviewTimeForPerson(ces.getInterviewTimeForPerson());
                cesFromDb.setInterviewTimeForDay(ces.getInterviewTimeForDay());
                cesFromDb.setQuota(ces.getQuota());
                cesDAO.update(cesFromDb);
                checkInterviewDate();
                LOGGER.info("Current CES was updated");
            } else {
                if (cesDAO.getPendingCES() != null) {
                    cesFromDb = cesDAO.getPendingCES();
                } else {
                    cesFromDb = new CES();
                    cesDAO.create(cesFromDb);
                }
                cesFromDb.setYear(ces.getYear());
                cesFromDb.setStartRegistrationDate(ces.getStartRegistrationDate());
                cesFromDb.setEndRegistrationDate(ces.getEndRegistrationDate());
                cesFromDb.setStartInterviewingDate(ces.getStartInterviewingDate());
                cesFromDb.setEndInterviewingDate(ces.getEndInterviewingDate());
                cesFromDb.setQuota(ces.getQuota());
                cesFromDb.setReminders(ces.getReminders());
                cesFromDb.setInterviewTimeForPerson(ces.getInterviewTimeForPerson());
                cesFromDb.setInterviewTimeForDay(ces.getInterviewTimeForDay());
                cesDAO.update(cesFromDb);
                startSessionDate();
                checkInterviewDate();
                LOGGER.info("CES was updated");
            }
        } catch (DAOException e) {
            LOGGER.warn("Can't set CES changes");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void deleteCES() {

    }



    private void checkInterviewDate() throws DAOException {
        CES ces = getCES();
        if ((ces.getEndInterviewingDate() != null) && (ces.getStartInterviewingDate() != null)) {
            switchToInterviewBegan();
            sessionExpiredDate();
        }
    }

    private void sessionExpiredDate() throws DAOException {
        String expiredDate = getCES().getEndInterviewingDate().toString() + " 00:00:00";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            Date date = dateFormatter.parse(expiredDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    CES ces = cesDAO.getCurrentCES();
                    ces.setStatusId(cesStatus.read(3).getId()); //id of expired session?
                    cesDAO.update(ces);
                    LOGGER.info("Status changed to 'expired'");
                } catch (Exception e) {
                    LOGGER.error("Failed to set expired date", e);
                } finally {
                    daoFactory.putConnection(connection);
                }
            }
        }, new Date(expiredDate));

    }
    private void startSessionDate() throws DAOException {
        String startDate = getCES().getStartRegistrationDate().toString() + " 00:00:00";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            Date date = dateFormatter.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    CES ces = cesDAO.getPendingCES();
                    ces.setStatusId(cesStatus.read(1).getId()); //id of current session?
                    cesDAO.update(ces);
                    LOGGER.info("Status changed to 'current'");
                } catch (Exception e) {
                    LOGGER.error("Failed to set start of session date", e);
                } finally {
                    daoFactory.putConnection(connection);
                }
            }
        }, new Date(startDate));
    }
    private void switchToInterviewBegan() throws DAOException {
        String startInterviewDate = getCES().getStartInterviewingDate().toString() + " 00:00:00";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            Date date = dateFormatter.parse(startInterviewDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    CES ces = cesDAO.getPendingCES();
                    ces.setStatusId(cesStatus.read(4).getId()); //id of current session?
                    cesDAO.update(ces);
                    LOGGER.info("Status changed to 'interview began'");
                } catch (Exception e) {
                    LOGGER.error("Failed to set start of session date", e);
                } finally {
                    daoFactory.putConnection(connection);
                }
            }
        }, new Date(startInterviewDate));
    }


}


