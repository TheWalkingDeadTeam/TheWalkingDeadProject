package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Application;

import java.util.List;

/**
 * Created by Rangar on 01.05.2016.
 */
public interface ApplicationDAO extends GenericDAO<Application, Integer> {
    Application getApplicationByUserCES(Integer user_id, Integer ces_id) throws DAOException;

    List<Application> getAllCESApplications(Integer ces_id) throws DAOException;
}
