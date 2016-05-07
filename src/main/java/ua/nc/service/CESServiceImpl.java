package ua.nc.service;

import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.InterviewerParticipationDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Max Morozov on 07.05.2016.
 */
public class CESServiceImpl implements CESService {

    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private CESDAO cesDAO = daoFactory.getCESDAO(daoFactory.getConnection());
    private InterviewerParticipationDAO interviewerParticipationDAO =
            daoFactory.getInterviewerParticipationDAO(daoFactory.getConnection());
    private ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(daoFactory.getConnection());
    private static final int MINUTES_PER_HOUR = 60;
    private static final int INTERVIEWERS_PER_STUDENT = 2;
    private static final int MILLIS_PER_DAY = 1000 * 60 * 60 * 24;

    @Override
    public CES getCurrentCES() {
        return null;
    }

    @Override
    public void enroll(UserDetailsImpl userDetails) {

    }

    @Override
    public Date planSchedule(Mail interviewerMail, Map<String, String> interviewerParameters,
                             Mail studentMail, Map<String, String> studentParameters) throws DAOException {
        CES ces = cesDAO.getCurrentCES();
        Date startDate = ces.getStartInterviewingDate();
        int hoursPerDay = ces.getInterviewTimeForDay();
        int timePerStudent = ces.getInterviewTimeForPerson();
        int reminderTime = ces.getReminders();
        List<User> interviewersList = interviewerParticipationDAO.getInterviewersForCurrentCES();
        List<User> studentsList = applicationDAO.getStudentsForCurrentCES();

        int studentsAmount = studentsList.size();
        int interviewersAmount = interviewersList.size();
        Date endDate = new Date(startDate.getTime() + studentsAmount / (MINUTES_PER_HOUR / timePerStudent * hoursPerDay)
                / interviewersAmount * INTERVIEWERS_PER_STUDENT);

        List<Date> interviewDates = new ArrayList<>();
        interviewDates.add(startDate);
        long currentTime = startDate.getTime();
        while (currentTime < endDate.getTime()) {
            currentTime += MILLIS_PER_DAY;
            interviewDates.add(startDate);
        }

        new MailServiceImpl().sendInterviewReminders(interviewDates, reminderTime, interviewerMail, interviewerParameters, studentMail,
                studentParameters, interviewersList, studentsList);
        return endDate;
    }

}
