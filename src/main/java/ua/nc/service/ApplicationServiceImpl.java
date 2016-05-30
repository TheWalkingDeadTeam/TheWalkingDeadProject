package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationDAO;
import ua.nc.entity.User;
import ua.nc.dao.CESDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Application;
import ua.nc.entity.CES;

import java.sql.Connection;

/**
 * Created by Hlib on 09.05.2016.
 */
public class ApplicationServiceImpl implements ApplicationService {

    private final static Logger LOGGER = Logger.getLogger(ApplicationService.class);
    private final static DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public Application getApplicationByUserForCurrentCES(int userId) {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesdao = DAO_FACTORY.getCESDAO(connection);
        ApplicationDAO applicationDAO = DAO_FACTORY.getApplicationDAO(connection);

        Application application = null;
        try {
            CES ces = cesdao.getCurrentCES();
            application = applicationDAO.getApplicationByUserCES(userId,ces.getId());
        } catch (DAOException ex){
            LOGGER.warn(ex.getMessage());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return application;
    }
}
