package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        UserDAO userDAO = factory.getUserDAO();
        RoleDAO roleDAO = factory.getRoleDAO();
        User user = null;
        try {
            user = userDAO.findUserByEmail(email);
            user.setRoles(roleDAO.findByEmail(email));
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
        DAOFactory factory = PostgreDAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        UserDAO userDAO = factory.getUserDAO();
        RoleDAO roleDAO = factory.getRoleDAO();
        try {
            int id = userDAO.create(user);
            roles.add(roleDAO.findByName("ROLE_STUDENT"));
            user.setRoles(roles);
            roleDAO.setRoleToUser(user.getRoles(), user);
            return user;
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    @Override
    public User getUserById(int id) {
        DAOFactory factory = PostgreDAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        UserDAO userDAO = factory.getUserDAO();
        try {
            User user = new User();
            user = userDAO.get(id);
            System.out.println(user);// toDo remove
            return user;
        } catch (DAOException e) {
            LOGGER.error(e);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public List<User> findUsersByName(String name) {
        DAOFactory factory = PostgreDAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        UserDAO userDAO = factory.getUserDAO();
        List<User> users = new ArrayList<User>();
        try {
            users.addAll(userDAO.findUsersByName(name));
            users.forEach(System.out::println);// toDo remove
            return users;
        } catch (SQLException e) {
            LOGGER.error(e);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return users;
    }

    @Override
    public void delete(int id) {
        DAOFactory factory = PostgreDAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
        UserDAO userDAO = factory.getUserDAO();
        try {
            userDAO.delete(id);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
    }

    public String json() {
        String str = "[{\n" +
                "    \"bookId\": 1,\n" +
                "        \"bookName\": \"aa\",\n" +
                "        \"chapter\": [{\n" +
                "        \"chapterId\": 1,\n" +
                "            \"chapterName\": \"xx\",\n" +
                "            \"book\": null\n" +
                "    }, {\n" +
                "        \"chapterId\": 2,\n" +
                "            \"chapterName\": \"yy\",\n" +
                "            \"book\": null\n" +
                "    }]\n" +
                "}, {\n" +
                "    \"bookId\": 2,\n" +
                "        \"bookName\": \"bb\",\n" +
                "        \"chapter\": [{\n" +
                "        \"chapterId\": 4,\n" +
                "            \"chapterName\": \"pp\",\n" +
                "            \"book\": null\n" +
                "    }, {\n" +
                "        \"chapterId\": 3,\n" +
                "            \"chapterName\": \"zz\",\n" +
                "            \"book\": null\n" +
                "    }]\n" +
                "}, {\n" +
                "    \"bookId\": 3,\n" +
                "        \"bookName\": \"cc\",\n" +
                "        \"chapter\": [{\n" +
                "        \"chapterId\": 5,\n" +
                "            \"chapterName\": \"qq\",\n" +
                "            \"book\": null\n" +
                "    }, {\n" +
                "        \"chapterId\": 6,\n" +
                "            \"chapterName\": \"rr\",\n" +
                "            \"book\": null\n" +
                "    }]\n" +
                "}, {\n" +
                "    \"bookId\": 4,\n" +
                "        \"bookName\": \"dd\",\n" +
                "        \"chapter\": [{\n" +
                "        \"chapterId\": 8,\n" +
                "            \"chapterName\": \"tt\",\n" +
                "            \"book\": null\n" +
                "    }, {\n" +
                "        \"chapterId\": 7,\n" +
                "            \"chapterName\": \"ss\",\n" +
                "            \"book\": null\n" +
                "    }]\n" +
                "}]";
    return str;
    }
}
