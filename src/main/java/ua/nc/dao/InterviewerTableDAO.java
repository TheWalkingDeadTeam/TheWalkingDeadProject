package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Interviewer;

import java.util.List;

/**
 * Created by Rangar on 28.05.2016.
 */
public interface InterviewerTableDAO {
    Integer getInterviewersCount(String pattern) throws DAOException;

    List<Interviewer> getInterviewersTable(Integer cesId) throws DAOException;

    List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset) throws DAOException;

    List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy) throws DAOException;

    List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy, Boolean asc) throws DAOException;

    List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern) throws DAOException;

    List<Interviewer> getInterviewersTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException;
}
