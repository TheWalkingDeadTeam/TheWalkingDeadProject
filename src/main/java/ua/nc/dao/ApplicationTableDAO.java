package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.StudentData;

/**
 * Created by Rangar on 28.05.2016.
 */
public interface ApplicationTableDAO {
    Integer getApplicationsCount(Integer cesId, String pattern) throws DAOException;

    String getFullQuery(Integer cesId) throws DAOException;

    StudentData getApplicationsTable(Integer cesId) throws DAOException;

    StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset) throws DAOException;

    StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy) throws DAOException;

    StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy, Boolean asc) throws DAOException;

    StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, String pattern) throws DAOException;

    StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy, String pattern) throws DAOException;

    StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy, String pattern, Boolean asc) throws DAOException;
}
