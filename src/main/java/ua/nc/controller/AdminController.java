package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.*;
import ua.nc.entity.profile.StudentData;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.RegistrationValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private static final Logger LOGGER = Logger.getLogger(AdminController.class);
    private final UserService userService = new UserServiceImpl();
    private final CESService cesService = new CESServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();
    private final InterviewerService interviewerService = new InterviewerServiceImpl();
    private final IntervieweeService intervieweeService = new IntervieweeServiceImpl();

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
                    LOGGER.info(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
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

    @RequestMapping(value = {"/remove"}, method = RequestMethod.POST, produces = "application/json")
    public void removeInterviewers(@RequestBody ArrayList<Integer> interviewersId) {
        CESService cesService = new CESServiceImpl();
        int cesId = cesService.getCurrentCES().getId();
        Iterator<Integer> iterator = interviewersId.iterator();
        while (iterator.hasNext()) {
            try {
                cesService.removeInterviewer(iterator.next(), cesId);
            } catch (DAOException e) {
                LOGGER.error("Can't Sign out interviewer");
                LOGGER.error(e);
            }
        }

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
    Integer studentsGetJSONSize() {
        StudentService studentService = new StudentServiceImpl();
        return studentService.getSize();
    }


    @RequestMapping(value = {"/students/search/{itemsPerPage}/{pageNumber}/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    StudentData studentsSearch(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("pattern") String pattern) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, (pageNumber * itemsPerPage - 10), pattern);
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        LOGGER.info("studData == "+studentData.toString());
        return studentData;
    }


    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public StudentData getStudents(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, (pageNumber * itemsPerPage - 10));
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        LOGGER.info("studData == "+studentData.toString());
        return studentData;
    }

    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public StudentData getStudentsBySort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") Integer sortType, @PathVariable("type") Boolean asc) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, asc);
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        return studentData;
    }


    /**
     * Takes a json file with students status changes
     *
     * @param status
     */
    @RequestMapping(value = {"/students"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HttpStatus studentStatus(@RequestBody Status status) {
        Status studentStatus = status;
        if (!status.getType().isEmpty() && (status.getValues().size() > 0)) {
            studentService.changeStatus(studentStatus.getType(), studentStatus.getValues());
            return HttpStatus.OK;
        } else {
            LOGGER.warn("Request type is not supported");
            return HttpStatus.BAD_REQUEST;
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

    @RequestMapping(value = {"/interviewers/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Interviewer> interviewGetJSON(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber) {
        List<Interviewer> interviewers;
        InterviewerService interviewerService = new InterviewerServiceImpl();
        interviewers = interviewerService.getInterviewer(itemsPerPage, (pageNumber * itemsPerPage - 10));
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
        return interviewers;
    }

    @RequestMapping(value = {"/interviewers/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Interviewer> interviewGetJSONSort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("type") Boolean asc) {
        List<Interviewer> interviewers;
        InterviewerService interviewerService = new InterviewerServiceImpl();
        interviewers = interviewerService.getInterviewer(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType,asc);
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
        LOGGER.info("interviewers.get - successful");
        return interviewers;

    }

    @RequestMapping(value = {"/interviewers/size"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer interviewGetJSONSize() {
        InterviewerService interviewerService = new InterviewerServiceImpl();
        return interviewerService.getInterviewerSize();
    }

    @RequestMapping(value = {"/interviewer/search/{itemsPerPage}/{pageNumber}/{sortType}/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Interviewer> interviewerSearch(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("pattern") String pattern) {
        List<Interviewer> interviewers;
        System.out.println(pattern);
        InterviewerService interviewerService = new InterviewerServiceImpl();
        interviewers = interviewerService.getInterviewer(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, pattern);
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
        System.out.println(interviewers);
        return interviewers;
    }

    @RequestMapping(value = {"/interviewers"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HttpStatus interviewerStatus(@RequestBody Status status) {
        Status interviewerStatus = status;
        if (!status.getType().isEmpty() && (status.getValues().size() > 0)) {
            interviewerService.changeStatus(interviewerStatus.getType(), interviewerStatus.getValues());
            return HttpStatus.OK;
        } else {
            LOGGER.warn("Request type is not supported");
            return HttpStatus.BAD_REQUEST;
        }
    }

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String usersView() {

        return "admin-user-view";
    }

    @RequestMapping(value = {"/users/size"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer usersGetJSONSize() {
        UserServiceImpl userService = new UserServiceImpl();
        return userService.getSize();
    }
    @RequestMapping(value = {"/users/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer usersGetJSONSize(@PathVariable("pattern") String pattern) {
        UserServiceImpl userService = new UserServiceImpl();
        return userService.getSize(pattern);
    }

    @RequestMapping(value = {"/users/search/{itemsPerPage}/{pageNumber}/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<UserRow> usersSearch(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("pattern") String pattern) {
        List<UserRow> userRows;
        UserService userService = new UserServiceImpl();
        userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - 10), pattern);
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
    }

    @RequestMapping(value = {"/users/search/{itemsPerPage}/{pageNumber}/{sortType}/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<UserRow> usersSearch(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("pattern") String pattern) {
        List<UserRow> userRows;
        UserService userService = new UserServiceImpl();
        userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - 10),sortType, pattern);
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
    }


    @RequestMapping(value = {"/users/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<UserRow> getUsers(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber) {
        List<UserRow> userRows;
        UserService userService = new UserServiceImpl();
        userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - 10));
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        LOGGER.warn("users != null");
        return userRows;
    }

    @RequestMapping(value = {"/users/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<UserRow> getUsersBySort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("type") Boolean asc) {
        List<UserRow> userRows;
        UserService userService = new UserServiceImpl();
        userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, asc);
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
    }

    @RequestMapping(value = {"/interviewee"}, method = RequestMethod.GET)
    public String intervieweeView() {
        return "admin-interviwee-view";
    }

    @RequestMapping(value = {"/interviewee/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IntervieweeRow> intervieweeGetJSON(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber) {
        List<IntervieweeRow> interviewees;
        IntervieweeService intervieweeService = new IntervieweeServiceImpl();
        interviewees = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - 10));
        if (interviewees == null) {
            LOGGER.warn("interviewee == null");
        }
        return interviewees;
    }

    @RequestMapping(value = {"/interviewee/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IntervieweeRow> intervieweeGetJSONSort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("type") Boolean asc) {
        List<IntervieweeRow> interviewee;
        IntervieweeService intervieweeService = new IntervieweeServiceImpl();
        interviewee = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType,asc);
        if (interviewee == null) {
            LOGGER.warn("interviewee == null");
        }
        LOGGER.info("interviewee.get - successful");
        return interviewee;

    }

    @RequestMapping(value = {"/interviewee/size"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer intervieweeGetJSONSize() {
        IntervieweeService intervieweeService = new IntervieweeServiceImpl();
        return intervieweeService.getIntervieweeSize();
    }

    @RequestMapping(value = {"/interviewee/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer intervieweeGetJSONSize(@PathVariable("pattern") String pattern) {
        IntervieweeService intervieweeService = new IntervieweeServiceImpl();
        return intervieweeService.getIntervieweeSize(pattern);
    }

    @RequestMapping(value = {"/interviewee/search/{itemsPerPage}/{pageNumber}/{sortType}/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<IntervieweeRow> intervieweeSearch(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("pattern") String pattern) {
        List<IntervieweeRow> interviewee;
        IntervieweeService intervieweeService = new IntervieweeServiceImpl();
        interviewee = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, pattern);
        if (interviewee == null) {
            LOGGER.warn("interviewers == null");
        }
        System.out.println(interviewee);
        return interviewee;
    }

    @RequestMapping(value = {"/interviewee"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HttpStatus intervieweeStatus(@RequestBody Status status) {
        Status intervieweeStatus = status;
        if (!status.getType().isEmpty() && (status.getValues().size() > 0)) {
            intervieweeService.changeStatus(intervieweeStatus.getType(), intervieweeStatus.getValues());
            return HttpStatus.OK;
        } else {
            LOGGER.warn("Request type is not supported");
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * Takes a json file with students status changes
     *
     * @param status
     */
    @RequestMapping(value = {"/users"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public HttpStatus userStatus(@RequestBody Status status) {
        Status userStatus = status;
        if (!status.getType().isEmpty() && (status.getValues().size() > 0)) {
            userService.changeStatus(userStatus.getType(), userStatus.getValues());
            return HttpStatus.OK;
        } else {
            LOGGER.warn("Request type is not supported");
            return HttpStatus.BAD_REQUEST;
        }


    }

    @RequestMapping(value = {"/mail-template"}, method = RequestMethod.GET)
    public String mail() {
        return "admin-mail-template";
    }


    @RequestMapping(value = {"/cesPost"}, method = RequestMethod.POST)
    public
    @ResponseBody
    CES getCES(@RequestBody CES ces) {
        try {
            cesService.setCES(ces);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return ces;
    }


    @RequestMapping(value = {"/cessettings"}, method = RequestMethod.GET)
    public String cesPage() {
        return "cessettings";
    }

    @RequestMapping(value = "/cessettings", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    CES ces() {
        try {
            System.out.println(cesService.getCES());
            return cesService.getCES();
        } catch (DAOException e) {
            LOGGER.error("DAO error");
            return null;
        }
    }


    @RequestMapping(value = {"/scheduler"}, method = RequestMethod.GET)
    public String schedulerView() {
        return "admin-scheduler";
    }

    @RequestMapping(value = {"/enroll-session"}, method = RequestMethod.GET)
    public String enrollmentSessionView() {
        return "admin-es-view";
    }


    @RequestMapping(value = {"/report"}, method = RequestMethod.GET)
    public String report() {
        return "admin-report-template";
    }
}
