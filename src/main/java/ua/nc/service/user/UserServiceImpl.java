package ua.nc.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Role;
import ua.nc.entity.User;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
public class UserServiceImpl implements UserService {
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    //private UserDAO userDAO = daoFactory.getUserDAO(daoFactory.getConnection());
    // private RoleDAO roleDAO = daoFactory.getRoleDAO(daoFactory.getConnection());
    private MailService mailService = new MailServiceImpl();

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
            System.out.println("DB exception");
        } finally {
            daoFactory.putConnection(connection);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        System.out.println("Create user service");
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        System.out.println("Create user service after dao");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        try {
            for (Role role : user.getRoles()) {
                roles.add(roleDAO.findByName(role.getName()));
            }
            user.setRoles(roles);
            System.out.println("Before creation");
            userDAO.createUser(user,user.getRoles());
            System.out.println("After creation");
            //roleDAO.setRoleToUser(user.getRoles(), user);
            mailService.sendMail(user.getEmail(), "Registration", "Welcome " + user.getName() + " ! \n NetCracker[TheWalkingDeadTeam] ");
            return user;
        } catch (DAOException e) {
            System.out.println("DB exception"); //toDo log4j
            return null;
        } finally {
            daoFactory.putConnection(connection);
        }


    }

    /**
     * Change User password
     *
     * @param user
     * @param password
     */
    @Override
    public void changePassword(User user, String password) {
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            System.out.println("User password has not been modified");
        } finally {
            daoFactory.putConnection(connection);
        }
    }
}
