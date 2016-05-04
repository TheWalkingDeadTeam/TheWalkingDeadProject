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
     * @return page with interviewer data
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
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 5,\n" +
                "    \"color\": \"blue\"\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 5,\n" +
                "    \"color\": \"blue\"\n" +
                "  },{\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 5,\n" +
                "    \"color\": \"blue\"\n" +
                "  },{\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 5,\n" +
                "    \"color\": \"blue\"\n" +
                "  },{\n" +
                "    \"id\": 5,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"university\": \"KPI\",\n" +
                "    \"devMark\": 10,\n" +
                "    \"hrMark\": 5,\n" +
                "    \"color\": \"blue\"\n" +
                "  }]";

    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public
    @ResponseBody
    void profileFields() {


    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getStudentById(@ModelAttribute("id") Integer id) {
        return "redirect:/profile/" + id;
    }

    /**
     * Method for view interview list from admin controlle panel
     *
     * @return page with interviewer data
     */
    @RequestMapping(value = {"/interview"}, method = RequestMethod.GET)
    public String interviewView() {

        return "admin-iter-view";
    }

    @RequestMapping(value = {"/interview/list"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String interviewGetJSON() {


        return "[{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"role\" : \"Admin\"\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"role\" : \"Admin\"\n" +
                "  },{\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"role\" : \"Admin\"\n" +
                "  },{\n" +
                "    \"id\": 4,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"role\" : \"Admin\"\n" +
                "  },{\n" +
                "    \"id\": 5,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"role\" : \"Admin\"\n" +
                "  },{\n" +
                "    \"id\": 6,\n" +
                "    \"name\": \"Abc\",\n" +
                "    \"surname\" : \"Ogurchik\",\n" +
                "    \"isActive\": \"1\",\n" +
                "    \"role\" : \"Admin\"\n" +
                "  }]";

    }
    @RequestMapping(value = "/interview", method = RequestMethod.POST)
    public
    @ResponseBody
    void activateDeactivateInterviwer() {


    }


    @RequestMapping(value = {"/mail"}, method = RequestMethod.GET)
    public String mail(){return "mail";}
}
