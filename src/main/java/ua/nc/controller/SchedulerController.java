package ua.nc.controller;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.Mail;
import ua.nc.entity.Scheduler;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;
import ua.nc.validator.RegistrationValidator;
import ua.nc.validator.SchedulerValidator;
import ua.nc.validator.ValidationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Alexander on 05.05.2016.
 */
@Controller
public class SchedulerController {
    private final Logger log = Logger.getLogger(SchedulerController.class);
    private final static String GEO_CODE_GOOGLE = "AIzaSyBzqTdqxQtAvZzhVZofehN2mvetgdYpZf0";
    private final static String DEFAULT_PLACE_LINK = "http://www.google.com/maps/place/lat,lng";
    private final static String LOCATION = "$location";
    private final static String COURSE_TYPE = "$courseType";
    private final static String GOOGLE_MAPS = "$googleMaps";
    private final static String CONTACT_INTERVIEWERS = "$contactInterviewers";
    private final static String CONTACT_STUDENTS = "$contactStudent";
    private final static String DATA_FORMAT = "yyyy-MM-dd HH:mm";
    private final static String INTERVIEW_START_TIME = "interviewDateStart";
    private final static String INTERVIEW_END_TIME = "interviewDateEnd";
    private final static Integer POST_REGISTRATION_STATUS = 3;
    private final CESService cesService = new CESServiceImpl();
    private final MailService mailService = new MailServiceImpl();

    /**
     * Produces direct google map link from geolocation
     *
     * @param location of the interview
     * @return link on google.maps.com
     */
    private String googleMapLink(String location) {
        GeoApiContext context = new GeoApiContext().setApiKey(GEO_CODE_GOOGLE);
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    location).await();
            String latitude = String.valueOf(results[0].geometry.location.lat);
            String longitude = String.valueOf(results[0].geometry.location.lng);
            String link = DEFAULT_PLACE_LINK.replaceAll("lat", latitude);
            link = link.replaceAll("lng", longitude);
            return link;
        } catch (Exception e) {
            log.warn("Failed to parse coordinates", e);
            return location;
        }
    }

    /**
     * Get map with parameters from Scheduler object
     * The default parameters:
     * 1)Location
     * 2)Google_Maps
     * 3) Course_type
     *
     * @param scheduler
     * @return map
     */
    private Map<String, String> param(Scheduler scheduler) {
        Map<String, String> interviewerParameters = new HashMap<>();
        interviewerParameters.put(LOCATION, scheduler.getLocations());
        interviewerParameters.put(GOOGLE_MAPS, googleMapLink(scheduler.getLocations()));
        interviewerParameters.put(COURSE_TYPE, scheduler.getCourseType());
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
        Mail interviewerMail = mailService.getMail(scheduler.getMailIdStaff());
        Mail studentMail = mailService.getMail(scheduler.getMailIdUser());
        Map<String, String> interviewerParameters = param(scheduler);
        Map<String, String> studentParameters = param(scheduler);
        interviewerParameters.put(CONTACT_INTERVIEWERS, scheduler.getContactStaff());
        studentParameters.put(CONTACT_STUDENTS, scheduler.getContactStudent());
        Date startDate = convertDate(scheduler.getInterviewTime());
        try {
            if (cesService.getCurrentCES().getStatusId() == POST_REGISTRATION_STATUS) {
                List<Date> interviewDates = cesService.planSchedule(startDate);
                mailService.sendInterviewReminders(interviewDates, interviewerMail, interviewerParameters,
                        studentMail, studentParameters);
            }
        } catch (DAOException e) {
            log.error("Check Scheduler parameters", e);
        }
    }


    @RequestMapping(value = "/admin/schedulerValidate", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    Set<ValidationError> schedulingImprove(@RequestBody Scheduler scheduler) {
        Mail interviewerMail = mailService.getMail(scheduler.getMailIdStaff());
        Mail studentMail = mailService.getMail(scheduler.getMailIdUser());
        Map<String, String> interviewerParameters = param(scheduler);
        Map<String, String> studentParameters = param(scheduler);
        interviewerParameters.put(CONTACT_INTERVIEWERS, scheduler.getContactStaff());
        studentParameters.put(CONTACT_STUDENTS, scheduler.getContactStudent());
        Date startDate = convertDate(scheduler.getInterviewTime());
        Set<ValidationError> errors = new LinkedHashSet<>();
        try {
            List<Date> interviewDates = cesService.planSchedule(startDate);
            Map<String, Object> check = new HashMap<>();
            check.put(INTERVIEW_END_TIME, interviewDates);
            check.put(INTERVIEW_START_TIME, convertDate(scheduler.getInterviewTime()));
            errors = new SchedulerValidator().validate(check);
            if (errors.isEmpty()) {
                mailService.sendInterviewReminders(interviewDates, interviewerMail, interviewerParameters,
                        studentMail, studentParameters);
            }
        } catch (DAOException e) {
            log.error("Check Scheduler date parameter", e);
        }
        System.out.println("error size" + errors.size());
        return errors;
    }


    /**
     * Converts string time to data object
     *
     * @param time
     * @return
     */
    private Date convertDate(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATA_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(time);
            return date;
        } catch (ParseException e) {
            log.error("Failed to parse time", e);
        }
        return date;
    }

}
