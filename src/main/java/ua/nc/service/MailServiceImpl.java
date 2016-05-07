package ua.nc.service;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.InterviewerParticipationDAO;
import ua.nc.dao.MailDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Alexander Haliy on 23.04.2016.
 */
@Configuration
@EnableScheduling
@Service("mailService")
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);
    private static final String PROTOCOL = "smtp";
    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final String USERNAME = "netcrackerua@gmail.com";
    private static final String PASSWORD = "netcrackerpwd";
    private static final long SLEEP = 1000;
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private MailDAO mailDAO = daoFactory.getMailDAO(daoFactory.getConnection());
    private CESDAO cesDAO = daoFactory.getCESDAO(daoFactory.getConnection());
    private InterviewerParticipationDAO interviewerParticipationDAO =
            daoFactory.getInterviewerParticipationDAO(daoFactory.getConnection());
    private ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(daoFactory.getConnection());
    private static ThreadPoolTaskScheduler scheduler;
    private static ThreadPoolTaskScheduler schedulerMassDeliveryService;
    private static final int POOL_SIZE = 2;
    private static final int POOL_SIZE_SCHEDULER = 10;
    private static final int MILLIS_PER_HOUR = 1000 * 60 * 60;
    private static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int INTERVIEWERS_PER_STUDENT = 2;


    static {
        scheduler = new ThreadPoolTaskScheduler();
        schedulerMassDeliveryService = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(POOL_SIZE);
        schedulerMassDeliveryService.setPoolSize(POOL_SIZE_SCHEDULER);
        scheduler.initialize();
        schedulerMassDeliveryService.initialize();
    }

    @Override
    public Mail createMail(String header, String body) {
        Mail mail = null;
        try {
            mail = new Mail();
            mail.setHeadTemplate(header);
            mail.setBodyTemplate(body);
            Mail newCreated = mailDAO.create(mail);
            System.out.println(newCreated.getId());
        } catch (DAOException e) {
            LOGGER.error("Bad Happened", e);
        }
        return mail;
    }

    @Override
    public void sendMail(String address, Mail mail) {
        sendMail(address, mail.getHeadTemplate(), mail.getBodyTemplate());
    }

    @Override
    public void sendMail(String address, String header, String body) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties properties = getMailProperties();
        mailSender.setProtocol(PROTOCOL);
        mailSender.setHost(HOST);
        mailSender.setPort(PORT);
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);
        mailSender.setJavaMailProperties(properties);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(header);
        message.setText(body);
        AsynchronousSender(message, mailSender);
        //mailSender.send(message);
    }

    /**
     * Async mail sending.
     *
     * @param message message to send.
     * @param mailSender sender.
     */
    private void AsynchronousSender(final SimpleMailMessage message, final MailSender mailSender) {
        scheduler.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.send(message);
                } catch (Exception e) {
                    LOGGER.error("Failed to send", e);
                }
            }
        });
    }

    @Override
    public void massDelivery(String dateDelivery, final List<User> users, final Mail mail,
                             final Map<String, String> parameters) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // String date format 2012-07-06 13:05:45
        try {
            Date date = dateFormatter.parse(dateDelivery);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        schedulerMassDeliveryService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    for (User i : users) {
                        //Sleep for a while, google may think you're spamming :(
                        Thread.sleep(SLEEP);
                        Mail customizedMail = customizeMail(mail, parameters);
                        sendMail(i.getEmail(), customizedMail);
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to send", e);
                }
            }
        }, new Date());
    }

    /**
     * Set all the custom mail parameters.
     *
     * @param mail mail to customize.
     * @param parameters set of parameters in form : "{pattern1:meaning1, ..., patternN:meaningN}".
     * @return customized mail.
     */
    private Mail customizeMail(Mail mail, Map<String, String> parameters) {
        //customize mail topic
        String head = mail.getHeadTemplate();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            head = head.replaceAll(param.getKey(), param.getValue());
        }
        //customize mail body
        String body = mail.getBodyTemplate();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            body = body.replaceAll(param.getKey(), param.getValue());
        }

        Mail result = new Mail();
        result.setBodyTemplate(body);
        result.setHeadTemplate(head);
        return result;
    }

    @Override
    public List<Mail> getAllMails() {
        List<Mail> mails = new ArrayList<>();
        try {
            mails = mailDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("Can't retrieve all mail", e);
        }
        return mails;
    }

    @Override
    public void updateMail(Mail mail) {
        try {
            mailDAO.update(mail);
        } catch (DAOException e) {
            LOGGER.error("Can't update mail", e);
        }
    }

    @Override
    public void deleteMail(Mail mail) {
        try {
            mailDAO.delete(mail);
        } catch (DAOException e) {
            LOGGER.error("Can't delete mail", e);
        }
    }

    @Override
    public Mail getMail(Integer id) {
        Mail mail = null;
        try {
            mail = mailDAO.get(id);
        } catch (DAOException e) {
        }
        return mail;
    }

    public List<Mail> getByHeaderMailTemplate(String header) {
        List<Mail> mails = new ArrayList<>();
        try {
            mails = mailDAO.getByHeader(header);
        } catch (DAOException e) {
            LOGGER.error("Can't handle mail template", e);
        }
        return mails;
    }

    /**
     * Configuration for mail delivery service
     *
     * @return the mail service properties.
     */
    private Properties getMailProperties() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtps.auth", "true");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.debug", "true");
        return mailProperties;
    }

    /**
     * Spread all the students by interview dates and send notifications to all the participants.
     *
     * @param interviewDates list of all the interview dates.
     * @param reminderTime time during that the notification needs to be sent.
     * @param interviewerMail email template to send to all the interviewers.
     * @param interviewerParameters parameters to set in interviewer template to personalize the emails.
     * @param studentMail email template to send to all the students.
     * @param studentParameters parameters to set in student template to personalize the emails.
     * @param interviewersList list of all the interviewers who take part in the current interview.
     * @param studentsList list of all the students who take part in the current interview.
     */
    private void sendInterviewReminders(List<Date> interviewDates, int reminderTime, Mail interviewerMail,
                                       Map<String, String> interviewerParameters, Mail studentMail,
                                       Map<String, String> studentParameters, List<User> interviewersList,
                                       List<User> studentsList) {
        int reminderMillis = reminderTime * MILLIS_PER_HOUR;
        int studentsPerDay = studentsList.size() / interviewDates.size() + 1;
        int todaysFirstStudent = 0;
        int todaysLastStudent = studentsPerDay;

        for (Date interviewDate : interviewDates) {
            List<User> todayStudents = studentsList.subList(todaysFirstStudent, Math.min(todaysLastStudent,
                    studentsList.size()));
            todaysFirstStudent = todaysLastStudent;
            todaysLastStudent += studentsPerDay;
            massDelivery(new Date(interviewDate.getTime() + reminderMillis).toString(), interviewersList,
                    interviewerMail, interviewerParameters);
            massDelivery(new Date(interviewDate.getTime() + reminderMillis).toString(), todayStudents, studentMail,
                    studentParameters);
        }
    }

    @Override
    public Date planSchedule(int hoursPerDay, Mail interviewerMail, Map<String, String> interviewerParameters,
                             Mail studentMail, Map<String, String> studentParameters) {
        Date startDate = new Date();
        int timePerStudent = 10;
        int reminderTime = 24;
        List<User> interviewersList = new ArrayList<>();
        List<User> studentsList = new ArrayList<>();
        try {
            startDate = cesDAO.getCurrentCES().getStartInterviewingDate();
            timePerStudent = cesDAO.getCurrentCES().getInterviewTimeForPerson();
            reminderTime = cesDAO.getCurrentCES().getReminders();
            interviewersList = interviewerParticipationDAO.getInterviewersForCurrentCES();
            studentsList = applicationDAO.getStudentsForCurrentCES();
        } catch (DAOException e) {
            LOGGER.error("Missing data about current course enrolment session", e);
        }

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

        sendInterviewReminders(interviewDates, reminderTime, interviewerMail, interviewerParameters, studentMail,
                studentParameters, interviewersList, studentsList);
        return endDate;
    }


}
