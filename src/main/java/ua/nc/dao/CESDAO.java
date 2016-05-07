package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

/**
 * Created by Rangar on 04.05.2016.
 */
public interface CESDAO extends GenericDAO<CES, Integer> {
    CES getCurrentCES() throws DAOException;

    void addCESField(int cesId, int fieldId);

    //мб это переместить в InterviewerParticipationDAO? По смыслу больше туда подходит
    void addInterviewerForCurrentCES(int interviewerId);
}
