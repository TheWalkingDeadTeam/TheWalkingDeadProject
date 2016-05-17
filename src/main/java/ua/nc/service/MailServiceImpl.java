package ua.nc.service;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ua.nc.dao.CESDAO;
import ua.nc.dao.IntervieweeDAO;
import ua.nc.dao.MailDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreConnectionPool;
import ua.nc.dao.postgresql.PostgreUserDAO;
import ua.nc.entity.*;

import java.sql.Connection;
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
    private static final String DATE_PATTERN = "$date";
    private static final String NAME_PATTERN = "$name";
    private static final String SURNAME_PATTERN = "$surname";
    private static final String START_HOURS_PATTERN = "$hours";
    private static final String START_MINS_PATTERN = "$minutes";
    private static final String REJECTED = "rejected";
    private static final String ACCEPTED_WORK = "work";
    private static final String ACCEPTED_COURSE = "course";
    private static final int MIN_DELIMITER = 30;
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private static final int POOL_SIZE = 5;
    private static final int POOL_SIZE_SCHEDULER = 10;
    private static final int MILLIS_PER_HOUR = 1000 * 60 * 60;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private static ThreadPoolTaskScheduler schedulerMassDeliveryService = new ThreadPoolTaskScheduler();
    private CESService cesService = new CESServiceImpl();

    static {
        scheduler.setPoolSize(POOL_SIZE);
        schedulerMassDeliveryService.setPoolSize(POOL_SIZE_SCHEDULER);
        scheduler.initialize();
        schedulerMassDeliveryService.initialize();
    }

    @Override
    public Mail createMail(String header, String body) {
        Connection connection = daoFactory.getConnection();
        MailDAO mailDAO = daoFactory.getMailDAO(connection);
        Mail mail = null;
        try {
            mail = new Mail();
            mail.setHeadTemplate(header);
            mail.setBodyTemplate(body);
            Mail newCreated = mailDAO.create(mail);
            LOGGER.debug(newCreated.getId());
        } catch (DAOException e) {
            LOGGER.warn("Mail not created ", e);
        } finally {
            daoFactory.putConnection(connection);
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
     * @param message    message to send.
     * @param mailSender sender.
     */
    private void AsynchronousSender(final SimpleMailMessage message, final MailSender mailSender) {
        scheduler.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mailSender.send(message);
                } catch (Exception e) {
                    LOGGER.warn("Failed to send", e);
                }
            }
        });
    }

    @Override
    public void massDelivery(Date date, final List<User> users, final Mail mail) {
        schedulerMassDeliveryService.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> nameParameters = new HashMap<>();
                    for (User i : users) {
                        //Sleep for a while, google may think you're spamming :(
                        Thread.sleep(SLEEP);
                        nameParameters.put(NAME_PATTERN, i.getName());
                        nameParameters.put(SURNAME_PATTERN, i.getSurname());
                        sendMail(i.getEmail(), customizeMail(mail, nameParameters));
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to send email", e);
                }
            }
        },date);
    }

    /**
     * Set all the predefined mail parameters.
     *
     * @param mail       mail to customize.
     * @param parameters set of parameters in form : "{pattern1:meaning1, ..., patternN:meaningN}".
     * @return customized mail.
     */
    private Mail customizeMail(Mail mail, Map<String, String> parameters) {
        //customize mail topic
        String head = mail.getHeadTemplate();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            head = head.replace(param.getKey(), param.getValue());
        }

        //customize mail body
        String body = mail.getBodyTemplate();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            body = body.replace(param.getKey(), param.getValue());
        }

        Mail result = new Mail();
        result.setBodyTemplate(body);
        result.setHeadTemplate(head);
        return result;
    }

    @Override
    public List<Mail> getAllMails() {
        Connection connection = daoFactory.getConnection();
        MailDAO mailDAO = daoFactory.getMailDAO(connection);
        List<Mail> mails = new ArrayList<>();
        try {
            mails = mailDAO.getAll();
        } catch (DAOException e) {
            LOGGER.error("Can't retrieve all mail", e);
        } finally {
            daoFactory.putConnection(connection);
        }
        return mails;
    }

    @Override
    public void updateMail(Mail mail) {
        Connection connection = daoFactory.getConnection();
        MailDAO mailDAO = daoFactory.getMailDAO(connection);
        try {
            mailDAO.update(mail);
        } catch (DAOException e) {
            LOGGER.error("Can't update mail", e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void deleteMail(Mail mail) {
        Connection connection = daoFactory.getConnection();
        MailDAO mailDAO = daoFactory.getMailDAO(connection);
        try {
            mailDAO.delete(mail);
        } catch (DAOException e) {
            LOGGER.error("Can't delete mail", e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public Mail getMail(Integer id) {
        Connection connection = daoFactory.getConnection();
        MailDAO mailDAO = daoFactory.getMailDAO(connection);
        Mail mail = null;
        try {
            mail = mailDAO.get(id);
        } catch (DAOException e) {
            LOGGER.error("Can't get mail", e);
        } finally {
            daoFactory.putConnection(connection);
        }
        return mail;
    }

    @Override
    public List<Mail> getByHeaderMailTemplate(String header) {
        List<Mail> mails = new ArrayList<>();
        Connection connection = daoFactory.getConnection();
        MailDAO mailDAO = daoFactory.getMailDAO(connection);
        try {
            mails = mailDAO.getByHeader(header);
        } catch (DAOException e) {
            LOGGER.error("Can't handle mail template", e);
        } finally {
            daoFactory.putConnection(connection);
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

    @Override
    public void sendInterviewReminders(List<Date> interviewDates, int reminderTime, Mail interviewerMail,
                                       Map<String, String> interviewerParameters, Mail studentMail,
                                       Map<String, String> studentParameters, Set<User> interviewersSet,
                                       Set<User> studentsSet, Map<Integer, Integer> applicationList) {
        int reminderMillis = reminderTime * MILLIS_PER_HOUR;
        Mail customizedInterviewerMail = customizeMail(interviewerMail, studentParameters);
        Mail customizedStudentMail = customizeMail(studentMail, studentParameters);
        String startHours = studentParameters.get(START_HOURS_PATTERN);
        String startMins = studentParameters.get(START_MINS_PATTERN);
        Map<String, String> dateTimeParameters = new HashMap<>();
        dateTimeParameters.put(START_HOURS_PATTERN, startHours);
        dateTimeParameters.put(START_MINS_PATTERN, startMins);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy");
        everydaySend(interviewDates, interviewersSet, studentsSet, reminderMillis, customizedInterviewerMail,
                customizedStudentMail, dateTimeParameters, dateFormat, applicationList);
    }


    /**
     * Substitutes Mail body with user parameters
     *
     * @param user parameters will be takend
     * @param mail to be substituted
     * @return
     */
    private Mail basicSubstituteBody(User user, Mail mail) {
        Map<String, String> substitution = new HashMap<>();
        substitution.put("$name", user.getName());
        substitution.put("$surname", user.getSurname());
        return customizeMail(mail, substitution);
    }


    @Override
    public void massDelivery(Set<User> users, Mail mail) {
        for (User i : users) {
            mail = basicSubstituteBody(i, mail);
            try {
                Thread.sleep(500);
                sendMail(i.getEmail(), mail);
            } catch (InterruptedException e) {
                LOGGER.error("Interrupted thread exception", e);
            }
        }
    }


    @Override
    public void sendFinalNotification() {
        Integer cesId = cesService.getCurrentCES().getId();
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = new PostgreUserDAO(connection);
        Set<User> jobOfferUsers = new HashSet<>();
        Set<User> courseAcceptedUsers = new HashSet<>();
        Set<User> courseRejectedUsers = new HashSet<>();
        try {
            jobOfferUsers = userDAO.getJobOfferedUsers(cesId);
        } catch (DAOException e) {
            LOGGER.error("Can't retrieve job offered students", e);
        }

        try {
            courseRejectedUsers = userDAO.getCourseRejectedStudents(cesId);
        } catch (DAOException e) {
            LOGGER.error("Can't retrieve course rejected students", e);
        }

        try {
            courseAcceptedUsers = userDAO.getCourseAcceptedUsers(cesId);
        } catch (DAOException e) {
            LOGGER.error("Can't retrieve course accepted students", e);
        } finally {
            daoFactory.putConnection(connection);
        }

        Mail mailRejectedTemplate = getByHeaderMailTemplate(REJECTED).get(0);
        Mail mailWorkOffer = getByHeaderMailTemplate(ACCEPTED_WORK).get(0);
        Mail mailCourseOffer = getByHeaderMailTemplate(ACCEPTED_COURSE).get(0);

        if ((!mailRejectedTemplate.getBodyTemplate().isEmpty()) && (!mailWorkOffer.getBodyTemplate().isEmpty()) &&
                (!mailCourseOffer.getBodyTemplate().isEmpty())) {

            Set<User> finalJobOfferUsers = jobOfferUsers;
            Set<User> finalCourseRejectedUsers = courseRejectedUsers;
            Set<User> finalCourseAcceptedUsers = courseAcceptedUsers;
            schedulerMassDeliveryService.schedule(new Runnable() {
                public void run() {
                    massDelivery(finalJobOfferUsers, mailWorkOffer);

                    massDelivery(finalCourseRejectedUsers, mailRejectedTemplate);

                    massDelivery(finalCourseAcceptedUsers, mailCourseOffer);
                }
            }, new Date());
        }
    }

    /**
     * Divide list of students to parts and send emails to each part in special day.
     *
     * @param interviewDates     all the dates when the interviews are performed.
     * @param interviewersSet    all the interviewers that take part in the CES.
     * @param studentsSet        all the students invited to interview.
     * @param reminderMillis     amount of milliseconds during that the reminder on a special date will be sent.
     * @param interviewerMail    email template to send to all the interviewers.
     * @param studentMail        email template to send to all the students.
     * @param dateTimeParameters interview start hours and minutes.
     * @param dateFormat         date formatter that transforms interview date to regional standard.
     */
    private void everydaySend(List<Date> interviewDates, Set<User> interviewersSet, Set<User> studentsSet,
                              int reminderMillis, Mail interviewerMail, Mail studentMail,
                              Map<String, String> dateTimeParameters, SimpleDateFormat dateFormat,
                              Map<Integer, Integer> applicationList) {
        Connection connection = daoFactory.getConnection();
        IntervieweeDAO intDAO = daoFactory.getIntervieweeDAO(connection);
        String startHours = dateTimeParameters.get(START_HOURS_PATTERN);
        String startMins = dateTimeParameters.get(START_MINS_PATTERN);
        List<User> interviewersList = new ArrayList<>(interviewersSet);
        List<User> studentsList = new ArrayList<>(studentsSet);
        int studentsPerDay = (int) Math.ceil(studentsList.size() / interviewDates.size());
        int todaysLastStudent = studentsPerDay;
        CESService cesService = new CESServiceImpl();
        int studentsPerGroup = cesService.getMinimalInterviewersAmount(interviewersSet);
        int firstStudent = 0;
        int lastStudent = studentsPerGroup;
        for (Date interviewDate : interviewDates) {
            dateTimeParameters.put(DATE_PATTERN, dateFormat.format(interviewDate));
            while (lastStudent < todaysLastStudent) {
                List<User> studentGroup = studentsList.subList(firstStudent, Math.min(lastStudent, studentsList.size()));
                int startMillis = Integer.parseInt(dateTimeParameters.get(START_HOURS_PATTERN)) * MILLIS_PER_HOUR +
                        Integer.parseInt(dateTimeParameters.get(START_MINS_PATTERN)) / MIN_DELIMITER * MILLIS_PER_MINUTE;
                Date reminderDate = new Date(interviewDate.getTime() + startMillis - reminderMillis);
                massDelivery(reminderDate, interviewersList, customizeMail(interviewerMail, dateTimeParameters));
                massDelivery(reminderDate, studentGroup, customizeMail(studentMail, dateTimeParameters));
                firstStudent = lastStudent;
                lastStudent += studentsPerGroup;
                dateTimeParameters = increaseGroupTime(dateTimeParameters);
                for (User user : studentGroup) {
                    int appId = applicationList.remove(user.getId());
                    try {
                        intDAO.create(new Interviewee(appId, new Date(interviewDate.getTime() + startMillis)));
                    } catch (DAOException e) {
                        LOGGER.error("Unable to create new interviewee for user " + user.getId());
                    }
                }
            }
            dateTimeParameters.put(START_HOURS_PATTERN, startHours);
            dateTimeParameters.put(START_MINS_PATTERN, startMins);
            todaysLastStudent += studentsPerDay;
        }
    }

    private Map<String, String> increaseGroupTime(Map<String, String> dateTimeParameters) {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            CES ces = cesDAO.getCurrentCES();
            int minutesPerStudent = ces.getInterviewTimeForPerson();
            int hours = Integer.parseInt(dateTimeParameters.get(START_HOURS_PATTERN));
            int minutes = Integer.parseInt(dateTimeParameters.get(START_MINS_PATTERN));
            minutes += minutesPerStudent;
            hours += minutes / 60;
            hours %= 24;
            minutes %= 60;
            dateTimeParameters.put(START_HOURS_PATTERN, Integer.toString(hours));
            dateTimeParameters.put(START_MINS_PATTERN, Integer.toString(minutes));
        } catch (DAOException e) {
            LOGGER.error("Current CES is absent.");
        }
        return dateTimeParameters;
    }


}
