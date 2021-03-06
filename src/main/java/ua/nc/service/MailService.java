package ua.nc.service;

import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param body   mail body.
     * @return new mail template.
     */
    Mail createMail(String header, String body);

    /**
     * Send email to recipient.
     *
     * @param address recipient email adress.
     * @param mail    template of email to send.
     */
    void sendMail(String address, Mail mail);

    /**
     * Send email  to recipient with explicit email template.
     * Async call function will return controll to the main flow.
     * Sends email with a delay to pass spam-filter.
     *
     * @param address recipient address.
     * @param header  mail topic.
     * @param body    mail body.
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
     * @param date  specific date mail to be send.
     * @param users who will get invitation.
     * @param mail  template.
     */
    void massDelivery(Date date, final List<User> users, final Mail mail);


    /**
     * Massive notification delivery
     *
     * @param users to send email notifications
     * @param mail  template
     */
    void massDelivery(Set<User> users, final Mail mail);

    /**
     * Spread all the students by interview dates and send notifications to all the participants.
     *
     * @param interviewDates        list of all the interview dates.
     * @param interviewerMail       email template to send to all the interviewers.
     * @param interviewerParameters parameters to set in interviewer template to personalize the emails.
     * @param studentMail           email template to send to all the students.
     * @param studentParameters     parameters to set in student template to personalize the emails.
     */
    void sendInterviewReminders(List<Date> interviewDates, Mail interviewerMail,
                                Map<String, String> interviewerParameters, Mail studentMail,
                                Map<String, String> studentParameters);

    /**
     * Send notification to all users when CES will be finished by admin. Notifications include
     * those who successfully passed their interviews(job offer, course participation) as well
     * the system will notify students who failed their interviews.
     *
     * @param rejectId - rejectId mail  to send
     * @param jobId    - jobID mail to send
     * @param courseId - courseID mail to send
     */
    void sendFinalNotification(Integer rejectId, Integer jobId, Integer courseId);

    /**
     * Send registration welcome letter to the participant
     * method will automatically choose letter template 'registration'
     * and compile letter template with user information
     * If there is no 'registration' template the system will choose
     * hardcoded template.
     */
    void sendRegistrationNotification(User user);
}
