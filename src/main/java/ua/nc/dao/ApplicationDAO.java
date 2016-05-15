package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Application;
import ua.nc.entity.User;

import java.util.List;

/**
 * Created by Rangar on 01.05.2016.
 */
public interface ApplicationDAO extends GenericDAO<Application, Integer> {
    Application getApplicationByUserCES(Integer userId, Integer cesId) throws DAOException;

    List<Application> getAllCESApplications(Integer cesId) throws DAOException;

    List<Application> getApplicationsByCesIdUserId(Integer cesId, List<Integer> userIds) throws DAOException;

    List<Application> getAllAcceptedApplications(Integer cesId) throws DAOException;
}
