package ua.nc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.MailDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreMailDAO;
import ua.nc.entity.Mail;
import ua.nc.entity.Scheduler;
import ua.nc.entity.User;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;
import ua.nc.validator.ValidationError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.*;

/**
 * Created by Alexander on 05.05.2016.
 */
@Controller
public class SchedulerController {
    private final Logger log = Logger.getLogger(UserController.class);
    private final static String GEO_CODE_GOOGLE = "AIzaSyCfZKoS6nurd-Hvf-Mb-A3R0yUNFAQ89-c";
    private final static String DEFAULT_PLACE_LINK = "http://www.google.com/maps/place/lat,lng";

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

    private Map<String, String> interviewParam(Scheduler scheduler) {
        Map<String, String> interviewerParameters = new HashMap<>();
        interviewerParameters.put("$location", scheduler.getLocations());
        interviewerParameters.put("$courseType", scheduler.getCourseType());
        interviewerParameters.put("$googleMaps", googleMapLink(scheduler.getLocations()));
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
        Map<String, String> interviewerParameters = interviewParam(scheduler);
        Map<String, String> studentParamets = interviewParam(scheduler);
        //studentParamets.put("$contact", scheduler.getContact());

        try {
            List<Date> planScheduler = cesService.planSchedule(interviewMail,interviewerParameters,studentMail,studentParamets);
        } catch (DAOException e){
            log.warn("Check Scheduler paramters",e);
        }
    }
}
