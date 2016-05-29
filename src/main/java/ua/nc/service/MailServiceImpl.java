package ua.nc.service;

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
import ua.nc.dao.MailDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreMailDAO;
import ua.nc.dao.postgresql.PostgreUserDAO;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.sql.Connection;
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
    private static final String REGISTRATION = "registration";
    private static final String DEFAULT_REG_MESSAGE = "We are we are happy to inform you that your have been successfully registered";
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);


    private static final int POOL_SIZE = 5;
    private static final int POOL_SIZE_SCHEDULER = 10;
    private static final int MILLIS_PER_MINUTE = 1000 * 60;
    private static final int MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
    private static final int MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
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
        }, date);
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
    public void sendInterviewReminders(List<Date> interviewDates, Mail interviewerMail,
                                       Map<String, String> interviewerParameters, Mail studentMail,
                                       Map<String, String> studentParameters) {
        CES ces = cesService.getCurrentCES();
        if (ces.getStatusId() == 3) {
            Connection connection = daoFactory.getConnection();
            UserDAO userDAO = daoFactory.getUserDAO(connection);
            ApplicationDAO appDAO = daoFactory.getApplicationDAO(connection);
            IntervieweeService intService = new IntervieweeServiceImpl();
            try {
                int reminderTime = ces.getReminders();
                Map<Integer, Integer> applicationList = appDAO.getAllAcceptedApplications(ces.getId());
                int reminderMillis = reminderTime * MILLIS_PER_HOUR;
                Mail customizedInterviewerMail = customizeMail(interviewerMail, studentParameters);
                Mail customizedStudentMail = customizeMail(studentMail, studentParameters);
                int studentsPerGroup = Math.min(userDAO.getDEVCount(ces.getId()), userDAO.getHRBACount(ces.getId()));
                int firstStudent = 0;
                int lastStudent = studentsPerGroup;
                List<User> interviewersList = new ArrayList<>(userDAO.getInterviewersForCurrentCES());
                List<User> studentsList = new ArrayList<>(userDAO.getAllAcceptedStudents(ces.getId()));
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyy HH:mm");
                Map<String, String> dateTimeParameters = new HashMap<>();
                Date previousDate = new Date(interviewDates.get(0).getTime() - reminderMillis - MILLIS_PER_DAY);
                for (Date interviewDate : interviewDates) {
                    long preciseTime = interviewDate.getTime();
                    Date estimatedTime = new Date(preciseTime - (preciseTime % (MILLIS_PER_MINUTE * 30)));
                    dateTimeParameters.put(DATE_PATTERN, dateFormat.format(estimatedTime));
                    List<User> studentGroup = studentsList.subList(firstStudent, Math.min(lastStudent, studentsList.size()));
                    Date reminderDate = new Date(interviewDate.getTime() - reminderMillis);
                    if (previousDate.getDate() != reminderDate.getDate()) {
                        massDelivery(reminderDate, interviewersList, customizeMail(customizedInterviewerMail, dateTimeParameters));
                        previousDate = reminderDate;
                    }
                    massDelivery(reminderDate, studentGroup, customizeMail(customizedStudentMail, dateTimeParameters));
                    intService.createInteviewees(studentGroup, applicationList, interviewDate);
                    firstStudent = lastStudent;
                    lastStudent += studentsPerGroup;
                }
                cesService.switchToInterviewingOngoing();
            } catch (DAOException e) {
                LOGGER.error(e.getCause());
            } finally {
                daoFactory.putConnection(connection);
            }
        }
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
        substitution.put(NAME_PATTERN, user.getName());
        substitution.put(SURNAME_PATTERN, user.getSurname());
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
    public void sendFinalNotification(Integer rejectId, Integer jobId, Integer courseId) {
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

        Mail mailRejectedTemplate = getMail(rejectId);
        Mail mailWorkOffer = getMail(jobId);
        Mail mailCourseOffer = getMail(courseId);

        if ((!mailRejectedTemplate.getBodyTemplate().isEmpty()) && (!mailWorkOffer.getBodyTemplate().isEmpty()) &&
                (!mailCourseOffer.getBodyTemplate().isEmpty())) {
//            Async send
//            final Set<User> finalJobOfferUsers = jobOfferUsers;
//            final Set<User> finalCourseRejectedUsers = courseRejectedUsers;
//            final Set<User> finalCourseAcceptedUsers = courseAcceptedUsers;
//            schedulerMassDeliveryService.schedule(new Runnable() {
//                public void run() {
//                    massDelivery(finalJobOfferUsers, mailWorkOffer);
//
//                    massDelivery(finalCourseRejectedUsers, mailRejectedTemplate);
//
//                    massDelivery(finalCourseAcceptedUsers, mailCourseOffer);
//                }
//            }, new Date());
            if (!jobOfferUsers.isEmpty()) {
                massDelivery(jobOfferUsers, mailWorkOffer);
            }
            if(!courseRejectedUsers.isEmpty()){
                massDelivery(courseRejectedUsers, mailRejectedTemplate);
            }
            if(!courseAcceptedUsers.isEmpty()){
                massDelivery(courseAcceptedUsers, mailCourseOffer);
            }

        }
    }

    @Override
    public void sendRegistrationNotification(User user) {
        List<Mail> mails = getByHeaderMailTemplate(REGISTRATION);
        Mail mail = new Mail();
        boolean statusExist = false;
        for (Mail m : mails) {
            if (!m.getBodyTemplate().isEmpty()) {
                mail = m;
                statusExist = true;
                break;
            }
        }
        if (statusExist) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put(NAME_PATTERN, user.getName());
            parameters.put(SURNAME_PATTERN, user.getSurname());
            mail = customizeMail(mail, parameters);
            sendMail(user.getEmail(), mail);
        } else {
            sendMail(user.getEmail(), "Registration", "Welcome " + user.getName() + " ! \n " + DEFAULT_REG_MESSAGE);
        }
    }


}
