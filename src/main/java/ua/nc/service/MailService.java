package ua.nc.service;

import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by Alexander on 23.04.2016.
 */
public interface MailService {

    void sendInterviewReminders(List<Date> interviewDates, int reminderTime, Mail interviewerMail,
                                Mail studentsMail, List<User> interviewersList, List<User> studentsList);

    Date planSchedule(int hoursPerDay, Mail interviewerMail, Mail studentsMail);

    public List<Mail> getAllMails();

    public void updateMail(Mail mail);

    public void deleteMail(Mail mail);

    public Mail getMail(Integer id);

    public Mail createMail(String header, String body);

    public void sendMail(String address, Mail mail);

    public void sendMail(String address, String header, String body);

    public List<Mail> getByHeaderMailTemplate(String header);

    public void massDelivery(String dateDelivery, final List<User> users, final Mail mail);

    public void sendInterviewReminders(List<Date> interviewDates, int studentHours, int devHours, int hrHours,
                                       int baHours, final Mail InterviewerMail, final Mail IntervieweeMail);
}
