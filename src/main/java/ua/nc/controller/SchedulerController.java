package ua.nc.controller;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Mail;
import ua.nc.entity.Scheduler;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;

import java.util.*;

/**
 * Created by Alexander on 05.05.2016.
 */
@Controller
public class SchedulerController {
    private final Logger log = Logger.getLogger(UserController.class);
    private final static String GEO_CODE_GOOGLE = "AIzaSyCfZKoS6nurd-Hvf-Mb-A3R0yUNFAQ89-c";
    private final static String DEFAULT_PLACE_LINK = "http://www.google.com/maps/place/lat,lng";
    //params
    private final static String LOCATION ="$location";
    private final static String COURSE_TYPE ="$courseType";
    private final static String GOOGLE_MAPS = "$googleMaps";
    private final static String CONTACT_INTERVIEWERS ="$contactInterviewers";
    private final static String CONTACT_STUDENTS ="$contactStudent";


    /**
     * Produces direct google map link to geolocation
     *
     * @param location of the interview
     * @return link on google.maps.com
     */
    private String googleMapLink(String location) {
        String link = null;
        GeoApiContext context = new GeoApiContext().setApiKey(GEO_CODE_GOOGLE);
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    location).await();
            String latitude = String.valueOf(results[0].geometry.location.lat);
            String longitude = String.valueOf(results[0].geometry.location.lng);
            link = DEFAULT_PLACE_LINK.replaceAll("lat", latitude);
            link = link.replaceAll("lng", longitude);
        } catch (Exception e) {
            log.warn("Failed to parse coordinates", e);
        }
        log.debug("Proceeded new place link:" + link);
        return link;
    }

    private Map<String, String> param(Scheduler scheduler) {
        Map<String, String> interviewerParameters = new HashMap<>();
        interviewerParameters.put(LOCATION, scheduler.getLocations());
        interviewerParameters.put(COURSE_TYPE, scheduler.getCourseType());
        interviewerParameters.put(GOOGLE_MAPS, googleMapLink(scheduler.getLocations()));
        return interviewerParameters;
    }


    /**
     * Scheduler Controller is responsible for student distribution between interview date
     * after distribution the system is automatically sending notification to all students
     * which were not rejected during current CES. The notifications  will be send after
     * some time determined by the system. Scheduler also sends notification to all interviewer
     * staff.
     *
     * @param scheduler
     */
    @RequestMapping(value = "/admin/scheduler", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void PostService(@RequestBody Scheduler scheduler) {
        System.out.println(scheduler);
        CESService cesService = new CESServiceImpl();
        MailService mailService = new MailServiceImpl();
        Mail interviewMail = mailService.getMail(scheduler.getMailIdStaff());
        Mail studentMail = mailService.getMail(scheduler.getMailIdUser());
        Map<String, String> interviewerParameters = param(scheduler);
        Map<String, String> studentParamets = param(scheduler);
        interviewerParameters.put(CONTACT_INTERVIEWERS, scheduler.getContactStaff());
        studentParamets.put(CONTACT_STUDENTS, scheduler.getContactStudent());
        try {
            List<Date> planScheduler = cesService.planSchedule();
        } catch (DAOException e) {
            log.warn("Check Scheduler paramters", e);
        }
    }
}
