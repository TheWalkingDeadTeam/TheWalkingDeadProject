package ua.nc.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.ApplicationForm;
import ua.nc.service.UserServiceImpl;

import java.io.IOException;

/**
 * Created by Neltarion on 23.04.2016.
 */
@Controller
public class ProfileController {

    private static final UserServiceImpl userService = new UserServiceImpl();

    private static void parseComplexJSON(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ApplicationForm applicationForm = new ApplicationForm();

        try {
            applicationForm = mapper.readValue(json, ApplicationForm.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode rootNode = mapper.readTree(json);
        JsonNode programingLanguages = rootNode.get("programingLanguages");
//        programingLanguages.forEach(System.out::println);
//        programingLanguages.forEach(p -> {
//            System.out.println(p.isValueNode());
//        });


        System.out.println("JSON mapped to class:\n" + applicationForm);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/newProfile", method = RequestMethod.GET)
    public String newProfile() {
        return "newProfile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApplicationForm> getAppForm(@RequestBody String json) throws IOException {
        System.out.println("JSON from page:\n" + json);
        ObjectMapper mapper = new ObjectMapper();
        ApplicationForm applicationForm = new ApplicationForm();

        applicationForm = mapper.readValue(json, ApplicationForm.class);
// class doesn't have required feilds yet.
        // no one cares
        // life is pain
        // we are wasting out time
        System.out.println(applicationForm);
        return new ResponseEntity<ApplicationForm>(applicationForm, HttpStatus.OK);
    }
}
