package ua.nc.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.entity.StudentStatus;
import ua.nc.entity.User;
import ua.nc.entity.profile.StudentData;
import ua.nc.service.StudentService;
import ua.nc.service.StudentServiceImpl;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.RegistrationValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private static final Logger LOGGER = Logger.getLogger(AdminController.class);
    private final UserService userService = new UserServiceImpl();
    StudentService studentService = new StudentServiceImpl();

    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        return "admin";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> registerUser(@RequestBody User user) {
        Validator validator = new RegistrationValidator();
        Set<ValidationError> errors = validator.validate(user);

        if (errors.isEmpty()) {
            if (userService.getUser(user.getEmail()) == null) {

                User registeredUser = userService.createUser(user);
                if (registeredUser == null) {
                    LOGGER.warn("Register failed " + user.getEmail());
                    errors.add(new ValidationError("register", "Register failed"));
                } else {
                    LOGGER.info("Admin" + ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                            .getPrincipal()).getUsername() + " create user " + user.getEmail());
                }
            } else {
                LOGGER.warn("User " + user.getEmail() + " already exists");
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

    @RequestMapping(value = {"/students/size"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String studentsGetJSONSize() {
        return "{\"size\":2000}";
    }


    @RequestMapping(value = {"/students/search"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String studentsSearch() {
        return "{\"size\":2000}";
    }


    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public StudentData getStudents(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, pageNumber);
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        return studentData;
    }

    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}/{sortType}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public StudentData getStudentsBySort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") Integer sortType) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, pageNumber, sortType);
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        return studentData;
    }


    /**
     * Takes a json file with students status changes
     *
     * @param studentStatus
     */
    @RequestMapping(value = {"/students"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void studentStatus(@RequestBody StudentStatus studentStatus) {
        StudentStatus status = studentStatus;
        if (!status.getType().isEmpty() && (status.getValues().size() > 0)) {
            studentService.changeStatus(status.getType(), status.getValues());
        } else {
            LOGGER.warn("Request type is not supported");
        }


    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET, produces = "application/json")
    public String getStudentById(@PathVariable("id") Integer id) {
        return "redirect:/profile?" + id;
    }

    /**
     * Method for view interview list from admin controlle panel
     *
     * @return page with interviewer data
     */
    @RequestMapping(value = {"/interviewers"}, method = RequestMethod.GET)
    public String interviewView() {
        return "admin-iter-view";
    }

    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String interviewGetJSON(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber) {
//        StudentData studentData;
//        StudentService studentService = new StudentServiceImpl();
//        studentData = studentService.getStudents(itemsPerPage, pageNumber);
//        if (studentData == null) {
//            LOGGER.warn("studData == null");
//        }
//        return studentData;
        return "[{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  }]";
    }

    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}/{sortType}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public StudentData interviewGetJSONSort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") Integer sortType) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, pageNumber, sortType);
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        return studentData;
    }

    @RequestMapping(value = {"/interviewers/list"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String interviewGetJSON() {


        return "[{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  },{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Abcac\",\n" +
                "    \"surname\": \"Pomidorchik\",\n" +
                "    \"email\" : \"ger@gmail.com\",\n" +
                "    \"role\" : \"Admin\",\n" +
                "    \"participation\": true\n" +
                "  }]";

    }


    @RequestMapping(value = {"/mail-template"}, method = RequestMethod.GET)
    public String mail() {
        return "admin-mail-template";
    }
}
