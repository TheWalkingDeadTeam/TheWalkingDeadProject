package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.dao.postgresql.PostgreRoleDAO;
import ua.nc.dao.postgresql.PostgreUserDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.Role;
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
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private static final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private static final int POOL_SIZE = 5;
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
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        List<CES> allCES = new ArrayList<>();
        try {
            allCES = cesdao.getAll();
            LOGGER.info("Successfully get all CES history");
        } catch (DAOException e) {
            LOGGER.warn("Can't get all CES history", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
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
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        CES ces = null;
        try {
            ces = cesdao.getCurrentCES();
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


    @Override
    public void enrollAsInterviewer(Integer userId, Integer cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        try {
            cesdao.addInterviewerForCurrentCES(cesId, userId);
            LOGGER.info("Successfully enrolled to current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't enroll as interviewer to current CES");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void removeInterviewer(Integer interviewerId, Integer cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = new PostgreCESDAO(connection);
        try {
            cesdao.removeInterviewerForCurrentCES(cesId, interviewerId);

            LOGGER.info("Successfully remove interviewer from current CES");
        } catch (DAOException e) {
            LOGGER.warn("Can't remove interviewer from current CES");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public List<Date> planSchedule(Date startDate) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESService cesService = new CESServiceImpl();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        CES ces = cesService.getCurrentCES();
        int hoursPerDay = ces.getInterviewTimeForDay();
        int timePerStudent = ces.getInterviewTimeForPerson();
        Set<User> studentsList = userDAO.getAllAcceptedStudents(ces.getId());
        int studentsAmount = studentsList.size();
        int studentsTogether = Math.min(userDAO.getDEVCount(ces.getId()), userDAO.getHRBACount(ces.getId()));
        List<Date> interviewDates = getInterviewDates(startDate, studentsAmount, studentsTogether, timePerStudent, hoursPerDay);
        updateInterViewingDate(startDate, interviewDates.get(interviewDates.size() - 1));
        daoFactory.putConnection(connection);
        return interviewDates;
    }

    @Override
    public void updateInterViewingDate(Date start, Date end) {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
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
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public CES getPendingCES() {
        Connection connection = daoFactory.getConnection();
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
            daoFactory.putConnection(connection);
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
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        if (cesDAO.getCurrentCES() != null) {
            return cesDAO.getCurrentCES();
        }
        daoFactory.putConnection(connection);
        return null;
    }

    @Override
    public void setCES(CES ces) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        CES cesFromDb = null;
        try {
            if (cesDAO.getCurrentCES() != null) {
                cesFromDb = cesDAO.getCurrentCES();
                cesFromDb.setQuota(ces.getQuota());
                System.out.println(ces.toString());
                cesDAO.update(cesFromDb);
                if (cesDAO.getCurrentCES().getStatusId() < 4) {
                    cesFromDb.setStartInterviewingDate(ces.getStartInterviewingDate());
                    cesFromDb.setEndInterviewingDate(ces.getEndInterviewingDate());
                    cesFromDb.setInterviewTimeForPerson(ces.getInterviewTimeForPerson());
                    cesFromDb.setInterviewTimeForDay(ces.getInterviewTimeForDay());
                    cesDAO.update(cesFromDb);
                    checkInterviewDate();
                }
                if (cesDAO.getCurrentCES().getStatusId() == 1) {
                    cesFromDb.setYear(ces.getYear());
                    cesFromDb.setStartRegistrationDate(ces.getStartRegistrationDate());
                    cesFromDb.setEndRegistrationDate(ces.getEndRegistrationDate());
                    cesFromDb.setReminders(ces.getReminders());
                    cesDAO.update(cesFromDb);
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
            LOGGER.warn("Can't set CES changes", e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void closeCES() {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        try {
            CES ces = cesDAO.getCurrentCES();
            if (ces != null) {
                ces.setStatusId(cesStatus.read(6).getId()); //id of 'Closed'
                cesDAO.update(ces);
                LOGGER.info("Status changed to 'Closed'");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to change status", e);
        } finally {
            daoFactory.putConnection(connection);
        }
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

    private void switchToPostRegistration() throws DAOException {
        String dateFromDB = getCES().getEndRegistrationDate().toString() + TIME_FOR_DATE_FROM_DB;
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        Date date = null;
        try {
            date = dateFormatter.parse(dateFromDB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    CES ces = cesDAO.getCurrentCES();
                    ces.setStatusId(cesStatus.read(3).getId()); //id of
                    cesDAO.update(ces);
                    LOGGER.info("Status changed to 'PostRegistration'");
                } catch (Exception e) {
                    LOGGER.error("Failed to change status", e);
                } finally {
                    daoFactory.putConnection(connection);
                }
            }
        }, date);

    }

    private void switchToPostInterviewing() throws DAOException {
        String dateFromDB = getCES().getEndInterviewingDate().toString() + TIME_FOR_DATE_FROM_DB;
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        Date date = null;
        try {
            date = dateFormatter.parse(dateFromDB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    CES ces = cesDAO.getCurrentCES();
                    ces.setStatusId(cesStatus.read(5).getId()); //id of
                    cesDAO.update(ces);
                    LOGGER.info("Status changed to 'PostInterviewing'");
                } catch (Exception e) {
                    LOGGER.error("Failed to change status", e);
                } finally {
                    daoFactory.putConnection(connection);
                }
            }
        }, date);
    }

    public void switchToRegistrationOngoing() throws DAOException {
        String dateFromDB = getCES().getStartRegistrationDate().toString() + TIME_FOR_DATE_FROM_DB;
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        Date date = null;
        try {
            date = dateFormatter.parse(dateFromDB);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    CES ces = cesDAO.getPendingCES();
                    ces.setStatusId(cesStatus.read(2).getId()); //id of current session?
                    cesDAO.update(ces);
                    LOGGER.info("Status changed to 'RegistrationOngoing'");
                } catch (Exception e) {
                    LOGGER.error("Failed to change status", e);
                } finally {
                    daoFactory.putConnection(connection);
                }
            }
        }, date);
    }

    public void switchToInterviewingOngoing() throws DAOException {
        final Connection connection = daoFactory.getConnection();
        final CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        final CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            CES ces = cesDAO.getPendingCES();
            ces.setStatusId(cesStatus.read(4).getId()); //id of current session?
            cesDAO.update(ces);
            LOGGER.info("Status changed to 'InterviewingOngoing'");
        } catch (Exception e) {
            LOGGER.error("Failed to change status", e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public boolean checkParticipation(Integer interviewerId) {
        Connection connection = daoFactory.getConnection();
        CESDAO cesdao = daoFactory.getCESDAO(connection);
        try {
            int cesId = getCurrentCES().getId();
            return cesdao.countInterviewerParticipation(cesId, interviewerId) > 0;
        } catch (DAOException ex){
            LOGGER.warn(ex);
        } finally {
            daoFactory.putConnection(connection);
        }
        return false;
    }
}