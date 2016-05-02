package ua.nc.service;

import ua.nc.entity.Mail;
import ua.nc.entity.User;

import java.util.List;

/**
 * Created by Alexander on 23.04.2016.
 */
public interface MailService {

     Mail getMail(Integer id);

     void createMail(String header, String body);

     void sendMail(String address, Mail mail);

     void sendMail(String address, String header, String body);

     List<Mail> getByHeaderMailTemplate(String header);

     void massDelivery(String dateDelivery, final List<User> users, final Mail mail);
}
