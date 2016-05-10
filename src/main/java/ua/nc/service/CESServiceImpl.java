package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.CESStatusDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;

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
    private static final int POOL_SIZE = 2;

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
    public void enroll(Integer userId, Integer currentCESId) throws DAOException {
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

    public CESServiceImpl() {
        scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);//
        scheduler.initialize();//
    }


    @Override
    public CES getCES() throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        if (cesDAO.getCurrentCES() != null){
            return cesDAO.getCurrentCES();
        } else if(cesDAO.getPendingCES() != null){
            return cesDAO.getPendingCES();
        }
        return null;
    }

    @Override
    public void setCES(CES ces) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESStatusDAO cesStatus = daoFactory.getCESStatusDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        CES cesFromDb;
        try {
            if (cesDAO.getCurrentCES() != null) {
                cesFromDb = cesDAO.getCurrentCES();
                if (cesFromDb.getStartInterviewingDate().compareTo(new Date()) == 1){
                    cesFromDb.setStartInterviewingDate(ces.getStartInterviewingDate());
                    cesFromDb.setEndInterviewingDate(ces.getEndInterviewingDate());
                    cesFromDb.setInterviewTimeForPerson(ces.getInterviewTimeForPerson());
                    cesFromDb.setInterviewTimeForDay(ces.getInterviewTimeForDay());
                    sessionExpiredDate(ces.getEndInterviewingDate().toString());
                }
                cesFromDb.setQuota(ces.getQuota());
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
                startSessionDate(ces.getStartRegistrationDate().toString());
                cesDAO.update(cesFromDb);
                LOGGER.info("CES was updated");
            }
        } catch (DAOException e) {
            LOGGER.warn("Can't set CES changes");
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }
    private void sessionExpiredDate(String expiredDate) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    private void startSessionDate(String startDate) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                    ces.setStatusId(cesStatus.read(2).getId()); //id of current session?
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


}


