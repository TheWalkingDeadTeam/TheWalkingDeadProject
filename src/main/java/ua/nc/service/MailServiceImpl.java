package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.nc.dao.MailDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Alexander Haliy on 23.04.2016.
 */

@Service("mailService")
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private MailDAO mailDAO = daoFactory.getMailDAO();
    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private static final String MAIL_EXCEPTION = "Something bad happened at your mail service system :(";
    /**
     * Create new Mail and store
     * it in db
     *
     * @param header
     * @param body
     */
    @Override
    public void createMail(String header, String body) {
        Mail mail = null;
        try {
            mail.setHeadTemplate(header);
            mail.setBodyTemplate(body);
            mailDAO.create(mail);
        } catch (DAOException e) {
            LOGGER.error(MAIL_EXCEPTION, e);
        }
    }

    /**
     * Send email to recipent
     *
     * @param address
     * @param mail
     */
    @Override
    public void sendMail(String address, Mail mail) {
        sendMail(address, mail.getHeadTemplate(), mail.getBodyTemplate());
    }

    /**
     * Send email  to recipient with concrete Mail entity
     * Async call function will return controll to the main flow
     * Sends email with delay 5 seconds, spam-filter will pass these
     *
     * @param address recipient address
     * @param header
     * @param body
     */
    @Override
    @Async
    @Scheduled(fixedDelay = 2000)
    public void sendMail(String address, String header, String body) {
        mailSender.setJavaMailProperties(getMailProperties());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(header);
        message.setText(body);
        mailSender.send(message);
    }

    /**
     * Ret
     *
     * @param id
     */
    @Override
    public Mail getMail(Integer id) {
        Mail mail = null;
        try {
            mail = mailDAO.get(id);
        } catch (DAOException e) {
        }
        return mail;
    }

    /**
     * Get Mails by Header
     *
     * @param header
     * @return
     */
    public List<Mail> getByHeaderMailTemplate(String header) {
        List<Mail> mails = new ArrayList<>();
        try {
            mails = mailDAO.getByHeader(header);
        } catch (DAOException e) {
            LOGGER.error(MAIL_EXCEPTION, e);
        }
        return mails;
    }


    /**
     * Configuration for mail mail delivery service
     *
     * @return propeties
     */
    private Properties getMailProperties() {
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");
        mailProperties.put("mail.debug", "true");
        mailProperties.put("mail.debug", "true");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.debug", true);
        mailProperties.put("username", "olexander.halii@gmail.com");
        mailProperties.put("password", "****");
        return mailProperties;
    }

    /**
     * Scheduld method - sends mails through period of time
     *  NOT IMPLEMENTED YET!!!
     *
     * @param users
     */
    @Async
    @Scheduled
    public void massDelivery(List<User> users, Mail mail) {
        for (User i : users) {
            sendMail(i.getEmail(), mail);
        }
    }

}
