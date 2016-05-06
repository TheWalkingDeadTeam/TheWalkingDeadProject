package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.User;

import java.util.List;

/**
 * Created by Max Morozov on 06.05.2016.
 */
public interface InterviewerParticipationDAO extends GenericDAO<CES, Integer> {
    List<User> getInterviewersForCurrentCES() throws DAOException;

    void addInterviewerForCurrentCES(int interviewerId);
}
