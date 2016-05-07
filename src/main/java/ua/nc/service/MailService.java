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

    /**
     * Get all the existing mail templates.
     *
     * @return all the templates list.
     */
    List<Mail> getAllMails();

    /**
     * Update existing mail template.
     *
     * @param mail the template with existing id.
     */
    void updateMail(Mail mail);

    /**
     * Delete specified mail template.
     *
     * @param mail the mail template to delete.
     */
    void deleteMail(Mail mail);

    /**
     * Retrieve mail template by id.
     *
     * @param id the template id.
     * @return mail template with the specified id
     */
    Mail getMail(Integer id);

    /**
     * Create new mail template and store it in db.
     *
     * @param header mail topic.
     * @param body mail body.
     * @return new mail template.
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
    void sendInterviewReminders(List<Date> interviewDates, int reminderTime, Mail interviewerMail,
                                Map<String, String> interviewerParameters, Mail studentMail,
                                Map<String, String> studentParameters, List<User> interviewersList,
                                List<User> studentsList);
}
