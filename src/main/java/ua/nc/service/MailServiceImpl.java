package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ua.nc.dao.MailDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
    private static final String DATE_PATTERN = "$Date";
    private static final String NAME_PATTERN = "$Name";
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private static final int POOL_SIZE = 2;
    private static final int POOL_SIZE_SCHEDULER = 10;
    private static ThreadPoolTaskScheduler scheduler;
    private static ThreadPoolTaskScheduler schedulerMassDeliveryService;
    private static final int MILLIS_PER_HOUR = 1000 * 60 * 60;

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
    public void massDelivery(String dateDelivery, final List<User> users, final Mail mail) {
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
                    Map<String, String> nameParameter = new HashMap<>();
                    for (User i : users) {
                        //Sleep for a while, google may think you're spamming :(
                        Thread.sleep(SLEEP);
                        nameParameter.put(NAME_PATTERN, i.getName());
                        sendMail(i.getEmail(), customizeMail(mail, nameParameter));
                    }
                } catch (Exception e) {
                    LOGGER.warn("Failed to send email", e);
                }
            }
        }, new Date(dateDelivery));
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
                                       Map<String, String> studentParameters, List<User> interviewersList,
                                       List<User> studentsList) {
        int reminderMillis = reminderTime * MILLIS_PER_HOUR;
        int studentsPerDay = (int) Math.ceil(studentsList.size() / interviewDates.size());
        int todaysFirstStudent = 0;
        int todaysLastStudent = studentsPerDay;
        Mail customizedInterviewerMail = customizeMail(interviewerMail, studentParameters);
        Mail customizedStudentMail = customizeMail(studentMail, studentParameters);

        //make everyday mail delivery
        Map<String, String> dateParameter = new HashMap<>();
        for (Date interviewDate : interviewDates) {
            List<User> todayStudents = studentsList.subList(todaysFirstStudent,
                    Math.min(todaysLastStudent, studentsList.size()));
            todaysFirstStudent = todaysLastStudent;
            todaysLastStudent += studentsPerDay;

            String todaysDate = new Date(interviewDate.getTime() - reminderMillis).toString();
            dateParameter.put(DATE_PATTERN, todaysDate);
            massDelivery(todaysDate, interviewersList, customizeMail(customizedInterviewerMail, dateParameter));
            massDelivery(todaysDate, todayStudents, customizeMail(customizedStudentMail, dateParameter));
        }
    }

}
