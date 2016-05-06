package ua.nc.service;

import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 23.04.2016.
 */
public interface MailService {

    Date planSchedule(int hoursPerDay, Mail interviewerMail, Map<String, String> interviewerParameters,
                      Mail studentMail, Map<String, String> studentParameters);

    List<Mail> getAllMails();

    void updateMail(Mail mail);

    void deleteMail(Mail mail);

    Mail getMail(Integer id);

    /**
     * Create new mail template and store it in db.
     *
     * @param header mail topic.
     * @param body mail body.
     */
    Mail createMail(String header, String body);

    /**
     * Send email to recipient.
     *
     * @param address recipient email adress.
     * @param mail template of email to send.
     */
    void sendMail(String address, Mail mail);

    /**
     * Send email  to recipient with explicit email template.
     * Async call function will return controll to the main flow.
     * Sends email with a delay to pass spam-filter.
     *
     * @param address recipient address.
     * @param header mail topic.
     * @param body mail body.
     */
    void sendMail(String address, String header, String body);

    /**
     * Get mail templates by topic.
     *
     * @param header mail topic.
     * @return template with such topic.
     */
    List<Mail> getByHeaderMailTemplate(String header);

    /**
     * Massive delivery service for async mailing.
     * Everything you need is to put time.
     *
     * @param dateDelivery specific date mail to be send.
     * @param users who will get invitation.
     * @param mail template.
     * @param parameters set of parameters in form : "{pattern1:meaning1, ..., patternN:meaningN}".
     */
    void massDelivery(String dateDelivery, final List<User> users, final Mail mail,
                      final Map<String, String> parameters);
}
