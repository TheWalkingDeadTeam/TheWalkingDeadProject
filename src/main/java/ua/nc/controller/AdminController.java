package ua.nc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.*;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ListValue;
import ua.nc.entity.profile.StudentData;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.*;

import java.io.IOException;
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

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Set<ValidationError> registerUser(@RequestBody User user, SecurityContextHolderAwareRequestWrapper request) {
        Validator validator = new RegistrationValidator();
        Set<ValidationError> errors = validator.validate(user);
        if (errors.isEmpty()) {
            User registeredUser = userService.createUser(user);
            if (registeredUser == null) {
                LOGGER.warn("Register failed " + user.getEmail());
                errors.add(new ValidationError("register", "Register failed"));
            } else {
                LOGGER.info(((UserDetailsImpl) ((Authentication) request.getUserPrincipal()).getDetails()).getUsername() + " create user " + user.getEmail());
            }
        }
        return errors;
    }

    /**
     * @return view of the page that register new Admin, HR, DEV, BA
     */
    @RequestMapping(value = {"/create-user"})
    public String createUser() {
        return "admin-create-user";
    }

    @RequestMapping(value = {"/remove-ces-interviewer"}, method = RequestMethod.POST)
    public
    @ResponseBody
    HttpStatus removeInterviewers(@RequestBody IntegerList integerList) {
        CES currentCES = cesService.getCurrentCES();
        if (currentCES != null) {
            int cesId = currentCES.getId();
            for (Integer userId : integerList.getValues()) {
                try {
                    cesService.removeInterviewer(userId, cesId);
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
        return studentService.getSize("");
    }


    @RequestMapping(value = {"/students/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer studentsGetJSONSize(@PathVariable("pattern") String pattern) {
        return studentService.getSize(pattern);
    }


    @RequestMapping(value = {"/students/search/{itemsPerPage}/{pageNumber}/{pattern}"}, method = RequestMethod.GET,
            produces = "application/json")
    public
    @ResponseBody
    StudentData studentsSearch(@PathVariable("itemsPerPage") Integer itemsPerPage,
                               @PathVariable("pageNumber") Integer pageNumber,
                               @PathVariable("pattern") String pattern) {
        StudentData studentData = studentService.getStudents(itemsPerPage,
                (pageNumber * itemsPerPage - itemsPerPage), pattern);
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        return studentData;
    }


    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public StudentData getStudents(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                   @PathVariable("pageNumber") Integer pageNumber) {
        StudentData studentData = studentService.getStudents(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage));
        if (studentData == null) {
            LOGGER.warn("studData == null");
        }
        return studentData;
    }

    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"},
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public StudentData getStudentsBySort(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                         @PathVariable("pageNumber") Integer pageNumber,
                                         @PathVariable("sortType") Integer sortType,
                                         @PathVariable("type") Boolean asc) {
        StudentData studentData = studentService.getStudents(itemsPerPage,
                (pageNumber * itemsPerPage - itemsPerPage), sortType, asc);
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

    /**
     * Method for view interview list from admin controller panel
     *
     * @return page with interviewer data
     */
    @RequestMapping(value = {"/interviewers"}, method = RequestMethod.GET)
    public String interviewView() {
        return "admin-iter-view";
    }

    @RequestMapping(value = {"/interviewers/list/{itemsPerPage}/{pageNumber}"},
            method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public List<Interviewer> interviewGetJSON(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                              @PathVariable("pageNumber") Integer pageNumber) {
        List<Interviewer> interviewers = interviewerService.getInterviewer(itemsPerPage,
                (pageNumber * itemsPerPage - itemsPerPage));
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
        return interviewers;

    }

    @RequestMapping(value = {"/interviewers/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Interviewer> interviewGetJSONSort(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                                  @PathVariable("pageNumber") Integer pageNumber,
                                                  @PathVariable("sortType") String sortType,
                                                  @PathVariable("type") Boolean asc) {
        List<Interviewer> interviewers = interviewerService.getInterviewer(itemsPerPage,
                (pageNumber * itemsPerPage - itemsPerPage), sortType, asc);
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
        return interviewers;

    }

    @RequestMapping(value = {"/interviewers/size"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer interviewGetJSONSize() {
        return interviewerService.getInterviewerSize("");
    }

    @RequestMapping(value = {"/interviewers/size/{pattern}"},
            method = RequestMethod.GET,
            produces = "application/json")
    public
    @ResponseBody
    Integer interviewGetJSONSize(@PathVariable("pattern") String pattern) {
        return interviewerService.getInterviewerSize(pattern);
    }

    @RequestMapping(value = {"/interviewer/search/{itemsPerPage}/{pageNumber}/{sortType}/{pattern}"},
            method = RequestMethod.GET,
            produces = "application/json")
    public
    @ResponseBody
    List<Interviewer> interviewerSearch(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                        @PathVariable("pageNumber") Integer pageNumber,
                                        @PathVariable("sortType") String sortType,
                                        @PathVariable("pattern") String pattern) {
        List<Interviewer> interviewers = interviewerService.getInterviewer(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage), sortType, pattern);
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
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
        return userService.getSize();
    }

    @RequestMapping(value = {"/users/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer usersGetJSONSize(@PathVariable("pattern") String pattern) {
        return userService.getSize(pattern);
    }

    @RequestMapping(value = {"/users/search/{itemsPerPage}/{pageNumber}/{pattern}"},
            method = RequestMethod.GET,
            produces = "application/json")
    public
    @ResponseBody
    List<UserRow> usersSearch(@PathVariable("itemsPerPage") Integer itemsPerPage,
                              @PathVariable("pageNumber") Integer pageNumber,
                              @PathVariable("pattern") String pattern) {
        List<UserRow> userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage), pattern);
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
    }

    @RequestMapping(value = {"/users/search/{itemsPerPage}/{pageNumber}/{sortType}/{pattern}"},
            method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<UserRow> usersSearch(@PathVariable("itemsPerPage") Integer itemsPerPage,
                              @PathVariable("pageNumber") Integer pageNumber,
                              @PathVariable("sortType") String sortType,
                              @PathVariable("pattern") String pattern) {
        List<UserRow> userRows = userService.getUser(itemsPerPage,
                (pageNumber * itemsPerPage - itemsPerPage), sortType, pattern);
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
    }


    @RequestMapping(value = {"/users/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public List<UserRow> getUsers(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                  @PathVariable("pageNumber") Integer pageNumber) {
        List<UserRow> userRows = userService.getUser(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage));
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
    }

    @RequestMapping(value = {"/users/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public List<UserRow> getUsersBySort(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                        @PathVariable("pageNumber") Integer pageNumber,
                                        @PathVariable("sortType") String sortType,
                                        @PathVariable("type") Boolean asc) {
        List<UserRow> userRows = userService.getUser(itemsPerPage,
                (pageNumber * itemsPerPage - itemsPerPage), sortType, asc);
        if (userRows == null) {
            LOGGER.warn("users == null");
        }
        return userRows;
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

    @RequestMapping(value = {"/interviewee"}, method = RequestMethod.GET)
    public String intervieweeView() {
        return "admin-interviwee-view";
    }

    @RequestMapping(value = {"/interviewee/list/{itemsPerPage}/{pageNumber}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IntervieweeRow> intervieweeGetJSON(@PathVariable("itemsPerPage") Integer itemsPerPage,
                                                   @PathVariable("pageNumber") Integer pageNumber) {
        List<IntervieweeRow> interviewees = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage));
        if (interviewees == null) {
            LOGGER.warn("interviewee == null");
        }
        return interviewees;
    }

    @RequestMapping(value = {"/interviewee/list/{itemsPerPage}/{pageNumber}/{sortType}/{type}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<IntervieweeRow> intervieweeGetJSONSort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("type") Boolean asc) {
        List<IntervieweeRow> interviewee = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage), sortType, asc);
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
        return intervieweeService.getIntervieweeSize("");
    }

    @RequestMapping(value = {"/interviewee/size/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer intervieweeGetJSONSize(@PathVariable("pattern") String pattern) {
        return intervieweeService.getIntervieweeSize(pattern);
    }

    @RequestMapping(value = {"/interviewee/search/{itemsPerPage}/{pageNumber}/{sortType}/{pattern}"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<IntervieweeRow> intervieweeSearch(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType, @PathVariable("pattern") String pattern) {
        List<IntervieweeRow> interviewee = intervieweeService.getInterviewee(itemsPerPage, (pageNumber * itemsPerPage - itemsPerPage), sortType, pattern);
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


    @RequestMapping(value = {"/mail-template"}, method = RequestMethod.GET)
    public String mail() {
        return "admin-mail-template";
    }


    @RequestMapping(value = {"/cesPost"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> getCES(@RequestBody CES ces) {
        CESValidator cesValidator = new CESValidator();
        Set<ValidationError> errors = cesValidator.validate(ces);
        if (errors.isEmpty()) {
            try {
                cesService.setCES(ces);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
        return errors;
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
            cesService.checkRegistrationDate();
            cesService.checkInterviewDate();
            return cesService.getCurrentCES();
        } catch (DAOException e) {
            LOGGER.error("Can`t get CES");
            return null;
        }
    }

    @RequestMapping(value = {"/cesclose"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    void closeCES(@RequestBody String params) {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer rejectionId = null;
        Integer workId = null;
        Integer courseId = null;
        try {
            JsonNode node = objectMapper.readValue(params, JsonNode.class);
            JsonNode rejectionNode = node.get("rejection");
            JsonNode workNode = node.get("work");
            JsonNode courseNode = node.get("course");
            rejectionId = rejectionNode.asInt();
            workId = workNode.asInt();
            courseId = courseNode.asInt();
        } catch (IOException e) {
            LOGGER.error("Failed to parse CESclose parameters", e);
        }
        mailService.sendFinalNotification(rejectionId, workId, courseId);
        cesService.closeCES();
    }

    @RequestMapping(value = {"/scheduler"}, method = RequestMethod.GET)
    public String schedulerView() {
        return "admin-scheduler";
    }

    @RequestMapping(value = {"/edit-form"}, method = RequestMethod.GET)
    public String editFormView() {
        if (cesService.getPendingCES() == null) {
//            return "error-ces-ongoing";
            return "edit-form-error";
        }
        return "edit-form";
    }

    @RequestMapping(value = {"/edit-form"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<Field> editFormGet(Integer ces_id) {
        if (cesService.getPendingCES() == null) {
            return null;
        }
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

    /**
     * @return view of the reports with admin header
     */
    @RequestMapping(value = {"/report"}, method = RequestMethod.GET)
    public String report() {
        return "admin-report-template";
    }

    @RequestMapping(value = {"/mail-personal"}, method = RequestMethod.GET)
    public String mailSend() {
        return "mail-send";
    }


}

