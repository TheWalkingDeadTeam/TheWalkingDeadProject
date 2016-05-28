package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.IntervieweeRow;

import java.util.List;

/**
 * Created by Rangar on 28.05.2016.
 */
public interface IntervieweeTableDAO {
    void updateIntervieweeTable(Integer cesId, Integer quota) throws DAOException;

    Integer getIntervieweeCount(Integer cesId, String pattern) throws DAOException;

    List<IntervieweeRow> getIntervieweeTable(Integer cesId) throws DAOException;

    List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset) throws DAOException;

    List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy) throws DAOException;

    List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy, Boolean asc) throws DAOException;

    List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern) throws DAOException;

    List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException;
}
