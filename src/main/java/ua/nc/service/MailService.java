package ua.nc.service;

import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.List;

/**
 * Created by Alexander on 23.04.2016.
 */
public interface MailService {
    public List<Mail> getAllMails();

    public void updateMail(Mail mail);

    public void deleteMail(Mail mail);

    public Mail getMail(Integer id);

    public Mail createMail(String header, String body);

    public void sendMail(String address, Mail mail);

    public void sendMail(String address, String header, String body);

    public List<Mail> getByHeaderMailTemplate(String header);

    public void massDelivery(String dateDelivery, final List<User> users, final Mail mail);
}
