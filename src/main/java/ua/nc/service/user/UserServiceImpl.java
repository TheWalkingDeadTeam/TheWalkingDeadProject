package ua.nc.service.user;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreUserDAO;
import ua.nc.dao.postgresql.PostgreUserTableDAO;
import ua.nc.entity.Role;
import ua.nc.entity.User;
import ua.nc.entity.UserRow;
import ua.nc.security.ApplicationContextProvider;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;
import ua.nc.service.UserDetailsImpl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private MailService mailService = new MailServiceImpl();
    @Autowired

    private PasswordEncoder passwordEncoder = ApplicationContextProvider.getApplicationContext().getBean("encoder", PasswordEncoder.class);


    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber) {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
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
        } finally {
            daoFactory.putConnection(connection);
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
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String pattern) {
        Connection connection = daoFactory.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber, pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }


    @Override
    public void changeStatus(String action, List<Integer> userIds) {
        if ("activate".equals(action)) {
            activateUsers(userIds);
        } else if ("deactivate".equals(action)) {
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
        } finally {
            daoFactory.putConnection(connection);
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
        } finally {
            daoFactory.putConnection(connection);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
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
    public User findUserById(Integer id) {
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        try {
            for (Role role : user.getRoles()) {
                roles.add(roleDAO.findByName(role.getName()));
            }
            user.setRoles(roles);
            userDAO.createUser(user);
/*            mailService.sendRegistrationNotification(user);*/
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
        user.setPassword(passwordEncoder.encode(password));
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
        try {
            user.setPassword(passwordEncoder.encode(testPassword));
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
        } finally {
            daoFactory.putConnection(connection);
        }
        return false;
    }

    public void activateUsers(List<Integer> userIds) {
        Connection connection = daoFactory.getConnection();
        try {
            Integer userId = ((UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal()).getId();
            PostgreUserDAO userDAO = (PostgreUserDAO) daoFactory.getUserDAO(connection);
            for (Integer id : userIds) {
                if (!userId.equals(id)) {
                    userDAO.activateUser(id);
                }
            }
        } catch (DAOException e) {
            LOGGER.warn("Cannot activate users");
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    public void deactivateUsers(List<Integer> userIds) {
        Connection connection = daoFactory.getConnection();
        try {
            Integer userId = ((UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal()).getId();
            PostgreUserDAO userDAO = (PostgreUserDAO) daoFactory.getUserDAO(connection);
            for (Integer id : userIds) {
                if (!userId.equals(id)) {
                    userDAO.deactivateUser(id);
                }
            }
        } catch (DAOException e) {
            LOGGER.warn("Cannot deactivate users");
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void changeRoles(String email, Set<Role> roles) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        try {
            User user = userDAO.findByEmail(email);
            RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
            Set<Role> newRoles = new HashSet<>();
            for (Role role : roles) {
                newRoles.add(roleDAO.findByName(role.getName()));
            }
            roleDAO.removeRolesFromUser(user);
            roleDAO.setRolesToUser(newRoles, user);
        } catch (DAOException e) {
            LOGGER.warn(e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
    }
}
