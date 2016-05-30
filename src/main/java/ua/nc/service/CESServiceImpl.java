package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.CESStatus;
import ua.nc.entity.User;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 06.05.2016.
 */
public class CESServiceImpl implements CESService {
    private final static Logger LOGGER = Logger.getLogger(CESServiceImpl.class);
    private static final String TIME_FOR_DATE_FROM_DB = " 00:00:00";

    private final DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private static final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private static final int POOL_SIZE = 3;
    private DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
    private Boolean registrationDateSet = false;
    private Boolean interviewDateSet = false;

    static {
        scheduler.setPoolSize(POOL_SIZE);
        scheduler.initialize();
    }


    @Override
    public List<CES> getAllCES() {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        List<CES> allCES = new ArrayList<>();
        try {
            allCES = cesdao.getAll();
            LOGGER.info("Successfully get all CES history");
        } catch (DAOException e) {
            LOGGER.warn("Can't get all CES history", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return allCES;
    }

    @Override
    public void checkRegistrationDate() throws DAOException {
        registrationDateSet = true;
        switchToRegistrationOngoing();
        switchToPostRegistration();
    }


    @Override
    public CES getCurrentCES() {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        CES ces = null;
        try {
            ces = cesdao.getCurrentCES();
            LOGGER.info("Current CES was gotten");
        } catch (DAOException e) {
            LOGGER.error("Can't get current CES", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return ces;
    }

    @Override
    public void enrollAsStudent(Integer userId, Integer currentCESId) throws DAOException {
        Connection connection = DAO_FACTORY.getConnection();
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
            DAO_FACTORY.putConnection(connection);
        }
    }


    @Override
    public void enrollAsInterviewer(Integer userId, Integer cesId) throws DAOException {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        try {
            cesdao.addInterviewerForCurrentCES(cesId, userId);
            LOGGER.info("Successfully enrolled to current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't enroll as interviewer to current CES");
            throw new DAOException(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void removeInterviewer(Integer interviewerId, Integer cesId) throws DAOException {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        try {
            cesdao.removeInterviewerForCurrentCES(cesId, interviewerId);

            LOGGER.info("Successfully remove interviewer from current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't remove interviewer from current CES");
            throw new DAOException(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public List<Date> planSchedule(Date startDate) throws DAOException {
        Connection connection = DAO_FACTORY.getConnection();
        CESService cesService = new CESServiceImpl();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        CES ces = cesService.getCurrentCES();
        int hoursPerDay = ces.getInterviewTimeForDay();
        int timePerStudent = ces.getInterviewTimeForPerson();
        Set<User> studentsList = userDAO.getAllAcceptedStudents(ces.getId());
        int studentsAmount = studentsList.size();
        int studentsTogether = Math.min(userDAO.getDEVCount(ces.getId()), userDAO.getHRBACount(ces.getId()));
        List<Date> interviewDates = getInterviewDates(startDate, studentsAmount, studentsTogether, timePerStudent, hoursPerDay);
        updateInterViewingDate(startDate, interviewDates.get(interviewDates.size() - 1));
        DAO_FACTORY.putConnection(connection);
        return interviewDates;
    }

    @Override
    public void updateInterViewingDate(Date start, Date end) {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        CES cesFromDb = null;
        try {
            if (cesDAO.getCurrentCES() != null) {
                cesFromDb = cesDAO.getCurrentCES();
                if (cesDAO.getCurrentCES().getStatusId() < 4) {
                    cesFromDb.setStartInterviewingDate(start);
                    cesFromDb.setEndInterviewingDate(end);
                    cesDAO.update(cesFromDb);
                    checkInterviewDate();
                } else {
                    LOGGER.warn("Can't change interviewing date");
                }
                LOGGER.info("CES was updated");
            } else {
                LOGGER.info("No current CES");
            }
        } catch (DAOException e) {
            LOGGER.warn("Can't change interviewing date", e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public CES getPendingCES() {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        CES ces = null;
        try {
            if (cesdao.getPendingCES() != null) {
                ces = cesdao.getPendingCES();
                LOGGER.info("Successfully get pending CES");
            } else {
                LOGGER.info("No pending CES now");
            }
        } catch (DAOException e) {
            LOGGER.warn("Can't get pending CES", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return ces;
    }

    private List<Date> getInterviewDates(Date startDate, int studentsAmount, int studentsTogether,
                                         int minutesPerStudent, int hoursPerDay) {
        int millisPerStudent = minutesPerStudent * MILLIS_PER_MINUTE;
        int millisPerDay = hoursPerDay * MILLIS_PER_HOUR;
        List<Date> interviewDates = new ArrayList<>();
        interviewDates.add(startDate);
        long currentDate = startDate.getTime();
        int currentTime = 0;
        int stud = studentsTogether;
        while (stud < studentsAmount) {
            if (currentTime + millisPerStudent < millisPerDay) {
                interviewDates.add(new Date(currentDate + currentTime));
                currentTime += millisPerStudent;
                stud += studentsTogether;
            } else {
                currentDate += MILLIS_PER_DAY;
                currentTime = 0;
            }
        }
        return interviewDates;
    }

    @Override
    public CES getCES() throws DAOException {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        CES ces = null;
        try {
            ces = cesDAO.getCurrentCES();
            LOGGER.info("Current ces was gotten");
        } catch (Exception e){
            LOGGER.error("Can`t get ces");
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return ces;
    }

    @Override
    public void setCES(CES ces) throws DAOException {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        CES cesFromDb;
        try {
            if (cesDAO.getCurrentCES() != null) {
                cesFromDb = cesDAO.getCurrentCES();
                cesFromDb.setQuota(ces.getQuota());
                cesDAO.update(cesFromDb);
                if (cesDAO.getCurrentCES().getStatusId() < 4) {
                    cesDAO.update(setFieldsForInterviewPeriod(ces, cesFromDb));
                    checkInterviewDate();
                }
                if (cesDAO.getCurrentCES().getStatusId() == 1) {
                    cesDAO.update(setFieldsForRegistrationPeriod(ces, cesFromDb));
                    checkRegistrationDate();
                }
            } else {
                ces.setStatusId(1);
                cesDAO.create(ces);
                checkRegistrationDate();
                checkInterviewDate();
            }
            LOGGER.info("CES was updated");
        } catch (DAOException e) {
            LOGGER.error("Can't set CES changes", e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void closeCES() {
        changeStatus(CLOSED_ID);
        scheduler.shutdown();
    }

    public void checkInterviewDate() throws DAOException {
//        if (interviewDateSet){
//            return;
//        }
        CES ces = getCES();
        if ((ces.getEndInterviewingDate() != null) && (ces.getStartInterviewingDate() != null)) {
            interviewDateSet = true;
            switchToPostInterviewing();
        }
    }


    private void switchToRegistrationOngoing() throws DAOException {
        String dateFromDB = getCES().getStartRegistrationDate().toString() + TIME_FOR_DATE_FROM_DB;
        runThreadForChangeStatus(dateFromDB, REGISTRATION_ONGOING_ID);
    }
    private void switchToPostRegistration() throws DAOException {
        String dateFromDB = getCES().getEndRegistrationDate().toString() + TIME_FOR_DATE_FROM_DB;
        runThreadForChangeStatus(dateFromDB, POST_REGISTRATION_ID);
    }
    public void switchToInterviewingOngoing() throws DAOException {
        if (getCurrentCES().getStatusId() != POST_REGISTRATION_ID){
            LOGGER.warn("Session is not in post registration status!");
            return;
        }
        changeStatus(INTERVIEWING_ONGOING_ID);
    }
    private void switchToPostInterviewing() throws DAOException {
        String dateFromDB = getCES().getEndInterviewingDate().toString() + TIME_FOR_DATE_FROM_DB;
        runThreadForChangeStatus(dateFromDB, POST_INTERVIEWING_ID);
    }
    private void runThreadForChangeStatus(String dateFromDB, final int statusId){
        Date date;
        try {
            date = dateFormatter.parse(dateFromDB);
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    changeStatus(statusId);
                }
            }, date);
        } catch (Exception e) {
            LOGGER.error("Can`t switch status to 'Registration ongoing'");
        }
    }

    private void changeStatus(int statusId){
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        try {
            CES ces = cesDAO.getCurrentCES();
            if (ces != null) {
                if (ces.getStatusId() >= statusId) {
                    LOGGER.info("Status already changed");
                } else {
                    ces.setStatusId(statusId);
                    cesDAO.update(ces);
                    LOGGER.info("Status changed");
                }
            }else {
                LOGGER.warn("Current CES does not exist");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to change status", e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }
    private CES setFieldsForInterviewPeriod(CES ces, CES cesFromDb){
        cesFromDb.setStartInterviewingDate(ces.getStartInterviewingDate());
        cesFromDb.setEndInterviewingDate(ces.getEndInterviewingDate());
        cesFromDb.setInterviewTimeForPerson(ces.getInterviewTimeForPerson());
        cesFromDb.setInterviewTimeForDay(ces.getInterviewTimeForDay());
        return cesFromDb;
    }
    private CES setFieldsForRegistrationPeriod(CES ces, CES cesFromDb){
        cesFromDb.setYear(ces.getYear());
        cesFromDb.setStartRegistrationDate(ces.getStartRegistrationDate());
        cesFromDb.setEndRegistrationDate(ces.getEndRegistrationDate());
        cesFromDb.setReminders(ces.getReminders());
        return cesFromDb;
    }

    @Override
    public boolean checkParticipation(Integer interviewerId) {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = DAO_FACTORY.getCESDAO(connection);
        try {
            int cesId = getCurrentCES().getId();
            return cesdao.countInterviewerParticipation(cesId, interviewerId) > 0;
        } catch (DAOException ex){
            LOGGER.warn(ex);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return false;
    }
}