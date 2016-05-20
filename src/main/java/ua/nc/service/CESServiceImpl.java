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
    private static final int INTERVIEWERS_PER_STUDENT = 2;
    private static final int MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

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
    public CES getCurrentCES() {
        LOGGER.info("GET CURCES START");
        Connection connection = daoFactory.getConnection();
        LOGGER.info("GET CURCES CONNCECTION");
        CESDAO cesdao = new PostgreCESDAO(connection);
        LOGGER.info("GET CURCES CESDAO");
        CES ces = null;
        try {
            ces = cesdao.getCurrentCES();
            LOGGER.info("Successfully get current CES");
            System.out.println(ces);
        } catch (DAOException e) {
            LOGGER.warn("Can't get current CES", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
            LOGGER.info("GET CURCES DAOLALA");
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
    public List<Date> planSchedule() throws DAOException {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = new PostgreUserDAO(connection);
        CESDAO cesDAO = new PostgreCESDAO(connection);
        //get parameters
        CES ces = cesDAO.getCurrentCES();
        Date startDate = ces.getStartInterviewingDate();
        int hoursPerDay = ces.getInterviewTimeForDay();
        int timePerStudent = ces.getInterviewTimeForPerson();

        Set<User> interviewersList = userDAO.getInterviewersForCurrentCES();
        Set<User> studentsList = userDAO.getAllAcceptedStudents(ces.getId());
        Date endDate = calculateEndDate(ces, startDate, hoursPerDay, timePerStudent, interviewersList, studentsList);

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

    /**
     * Calculate end interviews date using start date, amount of CES participators and CES time limits.
     *
     * @param ces current CES.
     * @param startDate date of interviews start.
     * @param hoursPerDay everyday interview duration.
     * @param timePerStudent time to interview a student by an interviewer.
     * @param interviewersList all the interviewers that take part in the CES.
     * @param studentsList all the students invited to interview.
     * @return date of interviews finish.
     */
    private Date calculateEndDate(CES ces, Date startDate, int hoursPerDay, int timePerStudent,
                                  Set<User> interviewersList, Set<User> studentsList) {
        System.out.println("Date calculating.");
        int studentsAmount = studentsList.size();
        int studsPerDayPerOneIntrwr = MINUTES_PER_HOUR * hoursPerDay / timePerStudent;
        int studentsPerDay = studsPerDayPerOneIntrwr * getMinimalInterviewersAmount(interviewersList);
        Date endDate = new Date(startDate.getTime() + (studentsAmount / studentsPerDay) * MILLIS_PER_DAY);
        ces.setEndInterviewingDate(endDate);
        System.out.println("Date calculated");
        return endDate;
    }

    @Override
    public int getMinimalInterviewersAmount(Set<User> interviewersList) {
        Connection connection = daoFactory.getConnection();
        RoleDAO roleDAO = new PostgreRoleDAO(connection);
        int devAmount = 0;
        int hrbaAmount = 0;
        System.out.println(0);
        for (User intrwr : interviewersList) {
            try {
                System.out.println(1);
                intrwr.setRoles(roleDAO.findByEmail(intrwr.getEmail()));
                System.out.println(2);
            } catch (DAOException e) {
                LOGGER.warn("Unable to get interviewers roles.", e);
            }
            for (Role role : intrwr.getRoles()) {
                System.out.println(3);
                if ("ROLE_DEV".equals(role.getName())) {
                    devAmount++;
                }
                if (("ROLE_HR".equals(role.getName())) || ("ROLE_BA".equals(role.getName()))) {
                    hrbaAmount++;
                }
                System.out.println(4);
            }
            System.out.println(devAmount + " " + hrbaAmount);
        }
        return Math.min(devAmount, hrbaAmount);
    }


    @Override
    public CES getCES() throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        if (cesDAO.getCurrentCES() != null) {
            return cesDAO.getCurrentCES();
        }
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
                    switchToRegistrationOngoing();
                    switchToPostRegistration();
                }
            } else {
                ces.setStatusId(1);
                cesDAO.create(ces);
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

    private void checkInterviewDate() throws DAOException {
        CES ces = getCES();
        if ((ces.getEndInterviewingDate() != null) && (ces.getStartInterviewingDate() != null)) {
            switchToInterviewingOngoing();
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

    private void switchToRegistrationOngoing() throws DAOException {
        String dateFromDB = getCES().getStartRegistrationDate().toString() + TIME_FOR_DATE_FROM_DB;
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

    void switchToInterviewingOngoing() throws DAOException {
        String dateFromDB = getCES().getStartInterviewingDate().toString() + TIME_FOR_DATE_FROM_DB;
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
        }, date);
    }

}