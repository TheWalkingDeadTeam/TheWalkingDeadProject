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

    void sendInterviewReminders(List<Date> interviewDates, int reminderTime, Mail interviewerMail,
                                Map<String, String> interviewerParameters, Mail studentMail,
                                Map<String, String> studentParameters, List<User> interviewersList,
                                List<User> studentsList);

    Date planSchedule(int hoursPerDay, Mail interviewerMail, Map<String, String> interviewerParameters,
                      Mail studentMail, Map<String, String> studentParameters);

    List<Mail> getAllMails();

    void updateMail(Mail mail);

    void deleteMail(Mail mail);

    Mail getMail(Integer id);

    Mail createMail(String header, String body);

    void sendMail(String address, Mail mail);

    void sendMail(String address, String header, String body);

    List<Mail> getByHeaderMailTemplate(String header);

    void massDelivery(String dateDelivery, final List<User> users, final Mail mail,
                      final Map<String, String> parameters);
}
