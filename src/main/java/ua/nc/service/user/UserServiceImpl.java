package ua.nc.service.user;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
@Service
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private MailService mailService = new MailServiceImpl();
    @Autowired

    private PasswordEncoder passwordEncoder = ApplicationContextProvider.getApplicationContext().getBean("encoder", PasswordEncoder.class);



    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber) {
        Connection connection = DAO_FACTORY.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc) {
        Connection connection = DAO_FACTORY.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber, orderBy, asc);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern) {
        Connection connection = DAO_FACTORY.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber, orderBy, pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public List<UserRow> getUser(Integer itemPerPage, Integer pageNumber, String pattern) {
        Connection connection = DAO_FACTORY.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersTable(itemPerPage, pageNumber, pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
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
        Connection connection = DAO_FACTORY.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersCount("");
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public Integer getSize(String pattern) {
        Connection connection = DAO_FACTORY.getConnection();
        PostgreUserTableDAO postgreUserTableDAO = new PostgreUserTableDAO(connection);
        try {
            return postgreUserTableDAO.getUsersCount(pattern);
        } catch (DAOException e) {
            LOGGER.warn("Can't get students", e.getCause());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        Connection connection = DAO_FACTORY.getConnection();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        RoleDAO roleDAO = DAO_FACTORY.getRoleDAO(connection);
        User user = null;
        try {
            user = userDAO.findByEmail(email);
            user.setRoles(roleDAO.findByEmail(email));
        } catch (DAOException e) {
            LOGGER.info("User not found");
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return user;
    }

    @Override
    public User findUserById(Integer id) {
        Connection connection = DAO_FACTORY.getConnection();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        RoleDAO roleDAO = DAO_FACTORY.getRoleDAO(connection);
        User user = null;
        try {
            user = userDAO.read(id);
            user.setRoles(roleDAO.findByEmail(user.getEmail()));
        } catch (DAOException e) {
            LOGGER.info("User not found");
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        Connection connection = DAO_FACTORY.getConnection();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        RoleDAO roleDAO = DAO_FACTORY.getRoleDAO(connection);
        System.out.println("before encode");
        System.out.println(passwordEncoder);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        try {
            for (Role role : user.getRoles()) {
                roles.add(roleDAO.findByName(role.getName()));
            }
            user.setRoles(roles);
            System.out.println("before create");
            userDAO.createUser(user);
            System.out.println("after create");
/*            mailService.sendRegistrationNotification(user);*/
            mailService.sendMail(user.getEmail(), "Registration", "Welcome " + user.getName() + " ! \n NetCracker[TheWalkingDeadTeam] ");
            return user;
        } catch (DAOException e) {
            LOGGER.warn("User " + user.getEmail() + " hasn't been created");
            return null;
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void changePassword(User user, String password) {
        Connection connection = DAO_FACTORY.getConnection();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        user.setPassword(passwordEncoder.encode(password));
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            LOGGER.info("User " + user.getEmail() + " password has not been modified");
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void recoverPass(User user) {
        Connection connection = DAO_FACTORY.getConnection();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        //Generation of the randomized alphanumeric string with size equaled 10
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        try {
            user.setPassword(passwordEncoder.encode(newPassword));
            userDAO.updateUser(user);
            mailService.sendMail(user.getEmail(), "Password recovery", "Welcome " + user.getName() + " ! \n NetCracker[TheWalkingDeadTeam] \n New password: \n" + newPassword);
        } catch (DAOException e) {
            LOGGER.info("Password recovery failed for user " + user.getEmail());
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public boolean checkRole(User user, String roleName) {
        Connection connection = DAO_FACTORY.getConnection();
        RoleDAO roleDAO = DAO_FACTORY.getRoleDAO(connection);
        try {
            return user.getRoles().contains(roleDAO.findByName(roleName));
        } catch (DAOException ex) {
            LOGGER.warn(ex);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return false;
    }

    @Override
    public void activateUsers(List<Integer> userIds) {
        DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        Connection connection = DAO_FACTORY.getConnection();

        try {
            PostgreUserDAO userDAO = (PostgreUserDAO) DAO_FACTORY.getUserDAO(connection);
            for (Integer id : userIds) {
                userDAO.activateUser(id);
                userDAO.updateUser(findUserById(id));
            }
        } catch (DAOException e) {
            LOGGER.warn("Cannot activate users");
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }


    @Override
    public void deactivateUsers(List<Integer> userIds) {
        DAOFactory DAO_FACTORY = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        Connection connection = DAO_FACTORY.getConnection();
        try {
            PostgreUserDAO userDAO = (PostgreUserDAO) DAO_FACTORY.getUserDAO(connection);
            for (Integer id : userIds) {
                userDAO.deactivateUser(id);
                userDAO.updateUser(findUserById(id));
            }
        } catch (DAOException e) {
            LOGGER.warn("Cannot deactivate users");
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void changeRoles(String email, Set<Role> roles) {
        Connection connection = DAO_FACTORY.getConnection();
        UserDAO userDAO = DAO_FACTORY.getUserDAO(connection);
        try {
            User user = userDAO.findByEmail(email);
            System.out.println(user.getName());
            RoleDAO roleDAO = DAO_FACTORY.getRoleDAO(connection);
            System.out.println(roleDAO);
            Set<Role> newRoles = new HashSet<>();
            for (Role role : roles) {
                newRoles.add(roleDAO.findByName(role.getName()));
            }
            roleDAO.removeRolesFromUser(user);
            roleDAO.setRolesToUser(newRoles, user);

        } catch (DAOException e) {
            DAO_FACTORY.putConnection(connection);
            LOGGER.warn("Cannot find user with email " + email + " in DB.");
        }
    }
}
