package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

import java.util.Date;
import java.util.List;


public interface CESService {
    /**
     * ID of statuses in constants. Same id`s must be written in database, in ces_status table
     */
    int PENDING_ID = 1;
    int REGISTRATION_ONGOING_ID = 2;
    int POST_REGISTRATION_ID = 3;
    int INTERVIEWING_ONGOING_ID = 4;
    int POST_INTERVIEWING_ID = 5;
    int CLOSED_ID = 6;

    /**
     *
     * @return Course enroll session, that ongoing now (status id can be from 1 to 5) or null
     */
    CES getCurrentCES();

    void setCES(CES ces);

    void closeCES();

    void switchToInterviewingOngoing();

    void checkRegistrationDate();

    void checkInterviewDate();

    void updateInterViewingDate(Date start, Date end);

    CES getPendingCES();

    void enrollAsStudent(Integer userId, Integer cesId) throws DAOException;

    void enrollAsInterviewer(Integer userId, Integer cesId) throws DAOException;

    void removeInterviewer(Integer interviewerId, Integer cesId) throws DAOException;

    List<CES> getAllCES();

    /**
     * Plan current interview schedule.
     *
     * @return list of dates of the current CES interviews.
     * @throws DAOException missing data about current course enrolment session.
     */
    List<Date> planSchedule(Date startDate) throws DAOException;

    boolean checkParticipation(Integer interviewerId);
}
