package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 03.05.2016.
 */
public interface CESService {
    int POST_INTERVIEWING_ID = 5;
    int INTERVIEWING_ONGOING_ID = 4;
    int POST_REGISTRATION_ID = 3;
    int REGISTRATION_ONGOING_ID = 2;
    int CLOSED_ID = 6;
    CES getCurrentCES();

    CES getCES() throws DAOException;

    void setCES(CES ces) throws DAOException;

    void closeCES();
    void enrollAsStudent(Integer userId, Integer cesId) throws DAOException;

    void enrollAsInterviewer(Integer userId, Integer cesId) throws DAOException;
    void removeInterviewer(Integer interviewerId, Integer cesId)throws DAOException;
    List<CES> getAllCES();
    void switchToInterviewingOngoing() throws DAOException;
    void checkRegistrationDate() throws DAOException;
    void checkInterviewDate() throws DAOException;
    /**
     * Plan current interview schedule.
     *
     * @return list of dates of the current CES interviews.
     * @throws DAOException missing data about current course enrolment session.
     */
    List<Date> planSchedule(Date startDate) throws DAOException;
    void updateInterViewingDate(Date start, Date end);
    CES getPendingCES();
    boolean checkParticipation (Integer interviewerId);
}
