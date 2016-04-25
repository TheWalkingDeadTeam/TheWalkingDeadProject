package ua.nc.controller;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringArrayDeserializer;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Neltarion on 23.04.2016.
 */
@Controller
public class ProfileController {

    private static final UserServiceImpl userService = new UserServiceImpl();

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST, produces="application/json", consumes="application/json")
    @ResponseBody
    public String sendAppForm(@RequestBody String json) throws IOException {
        System.out.println("JSON from page:\n" + json);
        parseComplexJSON(json);
        return "myString";
    }


    private static void parseComplexJSON (String json) throws IOException {
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
}

//    Iterator<String> fieldNames = rootNode.fieldNames();
//while (fieldNames.hasNext()) {
//        String fieldName = fieldNames.next();
//        JsonNode fieldValue = rootNode.get(fieldName);
//        if (fieldValue.isObject()) {
//        System.out.println(fieldName + " : ");
//
//        } else {
//        String value = fieldValue.asText();
//        System.out.println(fieldName + " : " + value);
//        }
//        }