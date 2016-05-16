package ua.nc.service.user;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.method.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.nc.dao.CESDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationTableDAO;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.dao.postgresql.PostgreUserDAO;
import ua.nc.dao.postgresql.PostgreUserTableDAO;
import ua.nc.entity.*;
import ua.nc.entity.profile.StudentData;
import ua.nc.service.CESServiceImpl;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;

import java.sql.Connection;
import java.util.*;

/**
 * Created by Pavel on 18.04.2016.
 */
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    //private UserDAO userDAO = daoFactory.getUserDAO(daoFactory.getConnection());
    // private RoleDAO roleDAO = daoFactory.getRoleDAO(daoFactory.getConnection());
    private MailService mailService = new MailServiceImpl();

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber) {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        }
        return null;
    }

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc) {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber, orderBy, asc);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        }
        return null;
    }

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber, orderBy, pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        }
        return null;
    }


    @Override
    public void changeStatus(String action, List<Integer> userIds) {
        if (Objects.equals(action, "activate")) {
            activateUsers(userIds);
        } else if (Objects.equals(action, "deactivate")) {
            deactivateUsers(userIds);
        } else {
            LOGGER.error(action + " action not supported");
        }
    }

    @Override
    public Integer getSize() {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersCount("");
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        }
        return null;
    }
    @Override
    public Integer getSize(String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersCount(pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        }
        return null;
    }

    @Override
    public User getUser(String email) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        User user = null;
        try {
            user = userDAO.findByEmail(email);
            user.setRoles(roleDAO.findByEmail(email));
        } catch (DAOException e) {
            LOGGER.info("User not found");
        } finally {
            daoFactory.putConnection(connection);
        }
        return user;
    }

    @Override
    public User getUser(Integer id) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        User user = null;
        try {
            user = userDAO.read(id);
            user.setRoles(roleDAO.findByEmail(user.getEmail()));
        } catch (DAOException e) {
            LOGGER.info("User not found");
        } finally {
            daoFactory.putConnection(connection);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        try {
            for (Role role : user.getRoles()) {
                roles.add(roleDAO.findByName(role.getName()));
            }
            user.setRoles(roles);
            userDAO.createUser(user, user.getRoles());
            //roleDAO.setRoleToUser(user.getRoles(), user);
            mailService.sendMail(user.getEmail(), "Registration", "Welcome " + user.getName() + " ! \n NetCracker[TheWalkingDeadTeam] ");
            return user;
        } catch (DAOException e) {
            LOGGER.warn("User " + user.getEmail() + " hasn't been created");
            return null;
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void changePassword(User user, String password) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            LOGGER.info("User " + user.getEmail() + " password has not been modified");
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public User recoverPass(User user) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        String testPassword = RandomStringUtils.randomAlphanumeric(10);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        try {
            user.setPassword(encoder.encode(testPassword));
            userDAO.updateUser(user);
            mailService.sendMail(user.getEmail(), "Password recovery", "Welcome " + user.getName() + " ! \n NetCracker[TheWalkingDeadTeam] \n New password \n" + testPassword);
            return user;
        } catch (DAOException e) {
            LOGGER.info("Password recovery failed for user " + user.getEmail());
            return null;
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public boolean checkRole(User user, String roleName) {
        Connection connection = daoFactory.getConnection();
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        try {
            return user.getRoles().contains(roleDAO.findByName(roleName));
        } catch (DAOException ex) {
            LOGGER.warn(ex);
        }
        return false;
    }

    @Override
    public void activateUsers(List<Integer> userIds) {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        Connection connection = daoFactory.getConnection();
        PostgreUserDAO userDAO = (PostgreUserDAO) daoFactory.getUserDAO(connection);
        for (Integer id : userIds) {
            try {
                userDAO.activateUser(id);
                userDAO.updateUser(getUser(id));
            } catch (DAOException e) {
                LOGGER.warn("Cannot activate user with id " + id);
            } finally {
                daoFactory.putConnection(connection);
            }

        }
        LOGGER.info("activation users - OK");
    }


    @Override
    public void deactivateUsers(List<Integer> userIds) {
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        Connection connection = daoFactory.getConnection();
        PostgreUserDAO userDAO = (PostgreUserDAO) daoFactory.getUserDAO(connection);
        for (Integer id : userIds) {
            try {
                userDAO.deactivateUser(id);
                userDAO.updateUser(getUser(id));
            } catch (DAOException e) {
                LOGGER.warn("Cannot deactivate user with id " + id);
            }
        }

        LOGGER.info("deactivation users - OK");
    }
}
