package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.PostgreCESDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;

import java.sql.Connection;

/**
 * Created by Pavel on 06.05.2016.
 */
public class CESServiceImpl implements CESService {
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
}
