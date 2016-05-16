package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.nc.entity.Mail;
import ua.nc.entity.User;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 30.04.2016.
 */

@RestController
public class MailController {
    private static final Logger LOGGER = Logger.getLogger(MailController.class);
    MailService mailService = new MailServiceImpl();
    UserService userService = new UserServiceImpl();

    /**
     * Retrieve all mail list from database
     *
     * @return mail list
     */
    @RequestMapping(value = "/mails/", method = RequestMethod.GET)
    public ResponseEntity<List<Mail>> listAllMails() {
        List<Mail> mails = mailService.getAllMails();
        if (mails.isEmpty()) {
            return new ResponseEntity<List<Mail>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Mail>>(mails, HttpStatus.OK);
    }

    /**
     * Retrieve mail by id
     *
     * @param id of the mail to be retrieved
     * @return mail
     */
    @RequestMapping(value = "/mails/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mail> getMail(@PathVariable("id") Integer id) {
        Mail mail = mailService.getMail(id);
        if (mail == null) {
            LOGGER.info("Mail with id" + id + "not found");
            return new ResponseEntity<Mail>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Mail>(mail, HttpStatus.OK);
    }

    /**
     * Create mail from template
     *
     * @param mail      template to be created
     * @param ucBuilder new Uri for currently created mail
     * @return headers and HTTP Created Status
     */
    @RequestMapping(value = "/mails/", method = RequestMethod.POST)
    public ResponseEntity<Void> createMail(@RequestBody Mail mail, UriComponentsBuilder ucBuilder) {
        LOGGER.debug("Creating mail:" + mail.getHeadTemplate() + mail.getBodyTemplate());
        mailService.createMail(mail.getHeadTemplate(), mail.getBodyTemplate());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/mails/{id}").buildAndExpand(mail.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    /**
     * Update mail
     *
     * @param id   of the mail to be updated
     * @param mail will replace origin mail
     * @return current updated mail
     */
    @RequestMapping(value = "/mails/{id}", method = RequestMethod.POST)
    public ResponseEntity<Mail> updateMail(@PathVariable("id") Integer id, @RequestBody Mail mail) {
        LOGGER.debug("Updating mail" + id);
        Mail mailCurrent = mailService.getMail(id);
        if (mailCurrent == null) {
            LOGGER.info("Mail with id" + id + "not found");
            return new ResponseEntity<Mail>(HttpStatus.NOT_FOUND);
        }
        mailCurrent.setBodyTemplate(mail.getBodyTemplate());
        mailCurrent.setHeadTemplate(mail.getHeadTemplate());
        mailService.updateMail(mail);
        return new ResponseEntity<Mail>(mailCurrent, HttpStatus.OK);
    }

    /**
     * Delete mail by id
     *
     * @param id of the mail to be deleted
     * @return success response
     */
    @RequestMapping(value = "/mails/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Mail> deleteMail(@PathVariable("id") Integer id) {
        Mail mail = mailService.getMail(id);
        if (mail == null) {
            LOGGER.info("Unable to delete mail" + id + "not found");
            return new ResponseEntity<Mail>(HttpStatus.NOT_FOUND);
        }
        mailService.deleteMail(mail);
        return new ResponseEntity<Mail>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete all mail from database
     *
     * @return HTTP STATUS.NO_CONTENT
     */
    @RequestMapping(value = "/mails/", method = RequestMethod.DELETE)
    public ResponseEntity<Mail> deleteAllMail() {
        LOGGER.debug("Deleting all mail");
        List<Mail> mails = mailService.getAllMails();
        for (Mail i : mails) {
            mailService.deleteMail(i);
        }
        return new ResponseEntity<Mail>(HttpStatus.NO_CONTENT);
    }


    /**
     * Customization email body template. The email template will be modified according
     * to parameters from Map
     *
     * @param mail       template to be modified
     * @param parameters to be changed in template
     * @return modified email template
     */
    private Mail customization(Mail mail, Map<String, String> parameters) {
        Mail mailRes = new Mail();
        String body = mail.getBodyTemplate();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            body = body.replace(param.getKey(), param.getValue());
        }
        mailRes.setBodyTemplate(body);
        mailRes.setHeadTemplate(mail.getHeadTemplate());
        return mailRes;
    }

    /**
     * Controller to handle custom/template mail from admin
     *
     * @param mail
     */
    @RequestMapping(value = "/admin/users-mail-id", method = RequestMethod.POST, produces = "application/json")
    public void sendMail(@RequestBody Mail mail) {
        List<Integer> userId = mail.getUsersId();
        if (mail.getMailIdUser() != null) {
            Mail studentMail = mailService.getMail(mail.getMailIdUser());
            for (Integer i : userId) {
                User user = userService.getUser(i);
                Map<String, String> customizeMail = new HashMap<>();
                customizeMail.put("$name", user.getName());
                customizeMail.put("$surname", user.getSurname());
                Mail mailUpdate = customization(studentMail, customizeMail);
                mailService.sendMail(user.getEmail(), mailUpdate);
            }
        } else {
            for (Integer i : userId) {
                User user = userService.getUser(i);
                mailService.sendMail(user.getEmail(), mail.getHeadTemplate(), mail.getBodyTemplate());
            }
        }
    }
}
