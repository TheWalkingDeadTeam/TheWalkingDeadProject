package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;

import java.util.List;

/**
 * Created by Alexander on 09.05.2016.
 */
@Controller
public class EnrollmentSessionController {
    private static final Logger LOGGER = Logger.getLogger(EnrollmentSessionController.class);
    private  CESService cesService = new CESServiceImpl();

    /**
     * Get all ES history
     * @return  Enrollment Session history
     */
    @RequestMapping(value = "/CES/", method = RequestMethod.GET)
    public ResponseEntity<List<CES>> listAllCes() {
        List<CES> ces = cesService.getAllCES();
        if (ces.isEmpty()) {
            return new ResponseEntity<List<CES>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<CES>>(ces, HttpStatus.OK);
    }

}

