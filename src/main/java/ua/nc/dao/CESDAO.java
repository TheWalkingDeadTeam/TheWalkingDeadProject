package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

/**
 * Created by Rangar on 04.05.2016.
 */
public interface CESDAO extends GenericDAO<CES, Integer> {
    CES getCurrentCES() throws DAOException;

    CES getPendingCES() throws DAOException;

    CES getRegistrationOngoingCES() throws DAOException;

    CES getPostRegistrationCES() throws DAOException;

    CES getInterviewingOngoingCES() throws DAOException;

    CES getPostInterviewingCES() throws DAOException;

    void addCESField(int cesId, int fieldId) throws DAOException;

    void removeCESField(int cesId, int fieldId) throws DAOException;

    void addInterviewerForCurrentCES(int cesId, int interviewerId) throws DAOException;

    void removeInterviewerForCurrentCES(int cesId, int interviewerId) throws DAOException;

    int countInterviewerParticipation(int cesId, int interviewerId) throws DAOException;
}
