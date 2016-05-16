package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.nc.entity.Mail;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;

import java.util.List;

/**
 * Created by Alexander on 30.04.2016.
 */

@RestController
public class MailController {
    private static final Logger LOGGER = Logger.getLogger(MailController.class);
    MailService mailService = new MailServiceImpl();

    /**
     * Retrieve all mail
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
     * @param id
     * @return
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
     * Create mail
     *
     * @param mail
     * @param ucBuilder
     * @return
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
     * @param id
     * @param mail
     * @return
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
     * @param id
     * @return
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
     * Delete all mail
     *
     * @return
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

}
