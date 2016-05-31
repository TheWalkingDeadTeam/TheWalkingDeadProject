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
     * Returns a CES object, that can be used for getting status of current CES or displayed on the form (or for
     * any another actions with CES).
     * This method always returns immediately, whether or not current CES exists
     *
     * @return Course enroll session, that ongoing now (status id can be from 1 to 5) or null
     * @see         CES
     */
    CES getCurrentCES();

    /**
     * Updates current course enroll session, if it exists or creates new CES (course enroll session). Method can be
     * used for editing settings of current CES or setup settings for new CES.
     *
     * @param ces course enroll session, that should be set as current
     * @see CES
     */
    void setCES(CES ces);

    /**
     * Closes (changes status to 'Closed') current CES, if exists. Status id of 'Closed' status = 6. Can be used for
     * manually closing of session.
     */
    void closeCES();

    /**
     * Switch status of current CES to "Interviewing Ongoing" (id of status = 4), if current CES exists and has status
     * 'Post registration'. Can be used for manually changing status of session.
     */
    void switchToInterviewingOngoing();

    /**
     * Check, if status of CES changed in depending of start registration date and end registration date.
     */
    void checkRegistrationDate();

    /**
     * Check, if status of CES changed in depending of start interviewing date and end interviewing date.
     */
    void checkInterviewDate();

    /**
     * Sets dates of interview period if this period has not started yet
     *
     * @param start date, when interview period will start
     * @param end   date, when interview period will finish
     */
    void updateInterViewingDate(Date start, Date end);

    /**
     * Returns a CES object, that can be used for checking status of current CES.
     * This method always returns immediately, whether or not pending CES exists
     *
     * @return Course enroll session, that has status "Pending" (status id = 1)
     * @see         CES
     */
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
