package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.nc.entity.User;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.RegistrationValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final Logger log = Logger.getLogger(LoginController.class);
    private final UserService userService = new UserServiceImpl();

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "admin";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> registerUser(@RequestBody User user) {
        Validator validator = new RegistrationValidator();
        Set<ValidationError> errors = new LinkedHashSet<>();
        if (errors.isEmpty()) {
            if (userService.getUser(user.getEmail()) == null) {

                User registeredUser = userService.createUser(user);
                if (registeredUser == null) {
                    log.warn("Register failed " + user.getEmail());
                    errors.add(new ValidationError("register", "Register failed"));
                }
            } else {
                log.warn("User " + user.getEmail() + " already exists");
                errors.add(new ValidationError("user", "Such user already exists"));
            }
        }
        return errors;
    }

    @RequestMapping(value = {"/create"})
    public String createUser() {
        return "admin-create-user";
    }

    /**
     * Method for view student list from admin controle panale UC 7
     *
     * @return page with students data
     */
    @RequestMapping(value = {"/students"}, method = RequestMethod.GET)
    public String studentsView() {

        return "admin-stud-view";
    }

    @RequestMapping(value = {"/students/list"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String studentsGetJSON() {


        return "[{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Abc Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 5,\n" +
                "    \"color\": \"blue\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 7,\n" +
                "    \"name\": \"Bcd Ananas\",\n" +
                "    \"isActive\": \"0\",\n" +
                "    \"university\": \"NAU\",\n" +
                "    \"devMark\": 15,\n" +
                "    \"hrMark\": 10,\n" +
                "    \"color\": \"yellow\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 8,\n" +
                "    \"name\": \"TEST Apelsin\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"NAU\",\n" +
                "    \"devMark\": 15,\n" +
                "    \"hrMark\": 10,\n" +
                "    \"color\": \"yellow\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Bcd Kokos\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 85,\n" +
                "    \"color\": \"green\"\n" +
                "  }]";

    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public
    @ResponseBody
    void profileFields() {
        System.out.println("URAAA");

    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getStudentById(@ModelAttribute("id") Integer id) {
        return "redirect:/profile/" + id;
    }

}
