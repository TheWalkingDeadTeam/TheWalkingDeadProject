package ua.nc.service;

import ua.nc.entity.Mail;

import java.util.List;

/**
 * Created by Alexander on 23.04.2016.
 */
public interface MailService {

    public Mail getMail(Integer id);

    public void createMail(String header, String body);

    public void sendMail(String address, Mail mail);

    public void sendMail(String address, String header, String body);

    public List<Mail> getByHeaderMailTemplate (String header);
}