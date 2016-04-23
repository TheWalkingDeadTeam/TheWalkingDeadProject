package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.entity.User;
import ua.nc.entity.enums.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User getUser(String email) {
        DAOFactory factory = PostgreDAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        UserDAO userDAO = factory.getUserDAO(Role.ROLE_STUDENT);
        User user = null;
        try {
            user = userDAO.findUserByEmail(email);
            System.out.println(user);
            user.setRoles(userDAO.findRoleByEmail(email));
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return user;
    }

    @Override
    public User createUser(User user) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        user.setPassword(encoder.encodePassword(user.getPassword(), null));
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_STUDENT);
        user.setRoles(roles);
        DAOFactory factory = PostgreDAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        UserDAO userDAO = factory.getUserDAO(Role.ROLE_STUDENT);
        try {
            int id = userDAO.create(user);
            System.out.println(id);
            userDAO.createRoleToUser(id, Role.ROLE_STUDENT);
            return user;
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }
}
