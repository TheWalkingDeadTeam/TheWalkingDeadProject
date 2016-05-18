package ua.nc.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.*;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ListValue;
import ua.nc.entity.profile.StudentData;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


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
    private final MailService mailService = new MailServiceImpl();

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

    @RequestMapping(value = {"/remove-ces-interviewer"}, method = RequestMethod.POST)
    public
    @ResponseBody
    HttpStatus removeInterviewers(@RequestBody IntegerList integerList) {
        CES currentCES = cesService.getCurrentCES();
        ;
        if (currentCES != null) {
            int cesId = currentCES.getId();
            Iterator<Integer> iterator = integerList.getInterviewersId().iterator();
            while (iterator.hasNext()) {
                try {
                    cesService.removeInterviewer(iterator.next(), cesId);
                } catch (DAOException e) {
                    LOGGER.error("Can't Sign out interviewer", e);
                    return HttpStatus.FOUND;
                }
            }
        }
        return HttpStatus.OK;
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
        return studentService.getSize("");
    }
    @RequestMapping(value = {"/students/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer studentsGetJSONSize(@PathVariable("pattern") String pattern) {
        StudentService studentService = new StudentServiceImpl();
        return studentService.getSize(pattern);
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
//        StudentData studentData;
//        StudentService studentService = new StudentServiceImpl();
//        studentData = studentService.getStudents(itemsPerPage, pageNumber);
//        if (studentData == null) {
//            LOGGER.warn("studData == null");
//        }
//        return studentData;
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
        interviewers = interviewerService.getInterviewer(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, asc);
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
        return interviewerService.getInterviewerSize("");
    }
    @RequestMapping(value = {"/interviewers/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer interviewGetJSONSize(@PathVariable("pattern") String pattern) {
        InterviewerService interviewerService = new InterviewerServiceImpl();
        return interviewerService.getInterviewerSize(pattern);
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
        userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, pattern);
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
        interviewee = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType, asc);
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
        return intervieweeService.getIntervieweeSize("");
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

    @RequestMapping(value = {"/cesclose"}, method = RequestMethod.POST)
    public
    @ResponseBody
    String closeCES() {
        System.out.println("admin");
        mailService.sendFinalNotification();
        cesService.closeCES();
        return null;
    }

    @RequestMapping(value = {"/scheduler"}, method = RequestMethod.GET)
    public String schedulerView() {
        return "admin-scheduler";
    }

    @RequestMapping(value = {"/edit-form"}, method = RequestMethod.GET)
    public String editFormView() {
        return "edit-form";
    }

    @RequestMapping(value = {"/edit-form"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Field> editFormGet(Integer ces_id) {
        EditFormService efs = new EditFormServiceImpl();
        List<Field> fields = new LinkedList<>();
        fields.addAll(efs.getAllFields(efs.getCES_ID()));
        return fields;
    }

    @RequestMapping(value = "/edit-form/appformfield/{listTypeID}/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<ListValue> getFieldInfoById(@PathVariable("listTypeID") Integer listType_id, @PathVariable("id") Integer id) {
        EditFormService efs = new EditFormServiceImpl();
        List<ListValue> listValues = new LinkedList<>();
        listValues.addAll(efs.getListValues(listType_id));
        return listValues;
    }

    @RequestMapping(value = "/edit-form/appformfield", method = RequestMethod.GET)
    public String getFieldInfo() {
        return "appformfield";
    }

    @RequestMapping(value = "/edit-form/new-question", method = RequestMethod.GET)
    public String addNewQuestion() {
        return "new-question";
    }

    @RequestMapping(value = "/edit-form/new-question", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> sendNewQuestion(@RequestBody FullFieldWrapper field) {
        Validator validator = new NewQuestionValidator();
        Set<ValidationError> errors = validator.validate(field);
        if (errors.isEmpty()) {
            EditFormService efs = new EditFormServiceImpl();
            field.setOrderNum(efs.newPositionNumber());
            efs.addNewQuestion(field);
        }
        return errors;
    }

    @RequestMapping(value = "/edit-form", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public
    @ResponseBody
    Set<ValidationError> deleteQuestion(@RequestBody ListWrapper id) {
        Validator validator = new DeleteQuestionValidator();
        Set<ValidationError> errors = validator.validate(id);
        if (errors.isEmpty()) {
            EditFormService efs = new EditFormServiceImpl();
            for (Integer idToWrite : id) {
                efs.deleteQuestionFromCES(efs.getCES_ID(), idToWrite);
            }
        }
        return errors;
    }

    @RequestMapping(value = "/edit-form/save-position", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> savePosition(@RequestBody FieldWrapper fields) {
        Validator validator = new SavePositionValidator();
        Set<ValidationError> errors = validator.validate(fields);
        if (errors.isEmpty()) {
            EditFormService efs = new EditFormServiceImpl();
            for (int i = 0; i < new LinkedList<Field>(fields.getFields()).size(); i++) {
                fields.getFields().get(i).setOrderNum(i + 1);
                efs.updatePosition(fields.getFields().get(i));
            }
        }
        return errors;
    }

    @RequestMapping(value = "/edit-form/appformfield/get-field/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Field getField(@PathVariable("id") Integer id) {
        EditFormService efs = new EditFormServiceImpl();
        Field field = efs.getField(id);
        return field;
    }

    @RequestMapping(value = {"/enroll-session"}, method = RequestMethod.GET)
    public String enrollmentSessionView() {
        return "admin-es-view";
    }

    @RequestMapping(value = {"/report"}, method = RequestMethod.GET)
    public String report() {
        return "admin-report-template";
    }

    @RequestMapping(value = {"/mail-personal"}, method = RequestMethod.GET)
    public String mailSend() {
        return "mail-send";
    }


}

