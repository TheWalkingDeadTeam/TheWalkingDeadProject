package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.sql.Connection;
import java.util.Set;

/**
 * Created by Hlib on 06.05.2016.
 */
public class AdminServiceImpl implements AdminService{

    private final static Logger LOGGER = Logger.getLogger(AdminServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    @Override
    public Set<User> getAllStudents() {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        Set<User> students = null;
        try {
            Role role = roleDAO.findByName("ROLE_STUDENT");
            students = userDAO.getByRole(role);
        } catch (DAOException ex){
            LOGGER.info("Couldn't get all students: " + ex.getMessage());
        } finally {
            daoFactory.putConnection(connection);
        }
        return students;
    }
}
