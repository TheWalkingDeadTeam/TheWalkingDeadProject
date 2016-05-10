package ua.nc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.MailDAO;
import ua.nc.dao.postgresql.PostgreMailDAO;
import ua.nc.entity.Mail;
import ua.nc.entity.User;
import ua.nc.service.MailService;
import ua.nc.validator.ValidationError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander on 05.05.2016.
 */
@Controller
public class SchedulerController {
    private final Logger log = Logger.getLogger(UserController.class);
    private final static String GEO_CODE_GOOGLE = "AIzaSyCfZKoS6nurd-Hvf-Mb-A3R0yUNFAQ89-c";
    private static final String LOCATION = "locations";
    private static final String MAIL_ID_USER = "mailIdUser";
    private static final String MAIL_ID_STAFF = "mailIdStaff";
    private static final String COURSE_TYPE = "courseType";
    private static final String CONTACTS = "contact";

    @RequestMapping(value = "/admin/scheduler", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void PostService(@RequestBody String json) {
        System.out.println("Hello from Scheduler the value is:" + json);
        //MailDAO mailDAO = new PostgreMailDAO();
        ObjectMapper objectMapper = new ObjectMapper();
        String location = null;
        Integer mailIdStudent = null;
        Integer mailIdStaff = null;
        String courseType = null;
        Integer contactNumber = null;
        try {
            JsonNode node = objectMapper.readValue(json, JsonNode.class);
            location = node.get(LOCATION).asText();
            mailIdStudent = node.get(MAIL_ID_USER).asInt();
            mailIdStaff = node.get(MAIL_ID_STAFF).asInt();
            courseType = node.get(COURSE_TYPE).asText();
            contactNumber = node.get(CONTACTS).asInt();
        } catch (IOException e) {
            log.warn("Failed to parse current location", e);
        }
        GeoApiContext context = new GeoApiContext().setApiKey(GEO_CODE_GOOGLE);
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    location).await();
            System.out.println("Latitude:" + results[0].geometry.location.lat);
            System.out.println("Longitude:" + results[0].geometry.location.lng);
        } catch (Exception e) {
            log.warn("Failed to parse coordinates", e);
        }

        //Mail interviewerMail =
    }
}
