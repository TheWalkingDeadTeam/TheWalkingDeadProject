package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Pavel on 03.05.2016.
 */
public interface CESService {
    CES getCurrentCES();

    void enroll(Integer userId, Integer cesId) throws DAOException;

    /**
     * Plan current interview schedule and send notifications to all the participants.
     *
     * @param interviewerMail       email template to send to all the interviewers.
     * @param interviewerParameters parameters to set in interviewer template to personalize the emails.
     * @param studentMail           email template to send to all the students.
     * @param studentParameters     parameters to set in student template to personalize the emails.
     * @return schedule of the current CES interviews.
     * @throws DAOException missing data about current course enrolment session.
     */
    List<Date> planSchedule(Mail interviewerMail, Map<String, String> interviewerParameters,
                            Mail studentMail, Map<String, String> studentParameters) throws DAOException;

}
