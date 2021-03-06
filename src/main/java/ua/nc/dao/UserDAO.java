package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 21.04.2016.
 */
public interface UserDAO extends GenericDAO<User, Integer> {
    User findByEmail(String email) throws DAOException;

    void createUser(User user) throws DAOException;

    void updateUser(User user) throws DAOException;

    void activateUser(Integer id) throws DAOException;

    void deactivateUser(Integer id) throws DAOException;

    Set<User> getByRole(Role role) throws DAOException;

    Set<User> getStudentsForCurrentCES() throws DAOException;

    Set<User> getInterviewersForCurrentCES() throws DAOException;

    Set<User> getAllAcceptedStudents(Integer cesId) throws DAOException;

    Set<User> getAllRejectedStudents(Integer cesId) throws DAOException;

    Set<User> getJobOfferedUsers(Integer cesId) throws DAOException;

    Set<User> getCourseAcceptedUsers(Integer cesId) throws DAOException;

    Set<User> getCourseRejectedStudents(Integer cesId) throws DAOException;

    Integer getDEVCount(Integer cesId) throws DAOException;

    Integer getHRBACount(Integer cesId) throws DAOException;

    Boolean isAppliedAsInterviewer(Integer userId, Integer cesId) throws DAOException;
}
