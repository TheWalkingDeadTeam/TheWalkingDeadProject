package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.*;

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
    String studentsGetJSONSize() {
        return "{\"size\":2000}";
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
        return studentData;
    }

    @RequestMapping(value = {"/students/list/{itemsPerPage}/{pageNumber}/{sortType}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public StudentData getStudentsBySort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") Integer sortType) {
        StudentData studentData;
        StudentService studentService = new StudentServiceImpl();
        studentData = studentService.getStudents(itemsPerPage, (pageNumber * itemsPerPage - 10), sortType);
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
        InterviewerService studentService = new InterviewerServiceImpl();
        interviewers = studentService.getInterviewer(itemsPerPage, pageNumber);
        if (interviewers == null) {
            LOGGER.warn("interviewers == null");
        }
        return interviewers;
//        return "[{\n" +
//                "    \"id\": 2,\n" +
//                "    \"name\": \"Abcac\",\n" +
//                "    \"surname\": \"Pomidorchik\",\n" +
//                "    \"email\" : \"ger@gmail.com\",\n" +
//                "    \"role\" : \"Admin\",\n" +
//                "    \"participation\": true\n" +
//                "  },{\n" +
//                "    \"id\": 2,\n" +
//                "    \"name\": \"Abcac\",\n" +
//                "    \"surname\": \"Pomidorchik\",\n" +
//                "    \"email\" : \"ger@gmail.com\",\n" +
//                "    \"role\" : \"Admin\",\n" +
//                "    \"participation\": true\n" +
//                "  },{\n" +
//                "    \"id\": 2,\n" +
//                "    \"name\": \"Abcac\",\n" +
//                "    \"surname\": \"Pomidorchik\",\n" +
//                "    \"email\" : \"ger@gmail.com\",\n" +
//                "    \"role\" : \"Admin\",\n" +
//                "    \"participation\": true\n" +
//                "  },{\n" +
//                "    \"id\": 2,\n" +
//                "    \"name\": \"Abcac\",\n" +
//                "    \"surname\": \"Pomidorchik\",\n" +
//                "    \"email\" : \"ger@gmail.com\",\n" +
//                "    \"role\" : \"Admin\",\n" +
//                "    \"participation\": true\n" +
//                "  },{\n" +
//                "    \"id\": 2,\n" +
//                "    \"name\": \"Abcac\",\n" +
//                "    \"surname\": \"Pomidorchik\",\n" +
//                "    \"email\" : \"ger@gmail.com\",\n" +
//                "    \"role\" : \"Admin\",\n" +
//                "    \"participation\": true\n" +
//                "  }]";
    }

    @RequestMapping(value = {"/interviewers/list/{itemsPerPage}/{pageNumber}/{sortType}"}, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public void interviewGetJSONSort(@PathVariable("itemsPerPage") Integer itemsPerPage, @PathVariable("pageNumber") Integer pageNumber, @PathVariable("sortType") String sortType) {
//        StudentData studentData;
//        StudentService studentService = new StudentServiceImpl();
//        studentData = studentService.getStudents(itemsPerPage, pageNumber, sortType);
//        if (studentData == null) {
//            LOGGER.warn("studData == null");
//        }
//        return studentData;

    }

    @RequestMapping(value = {"/interviewers/size"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Integer interviewGetJSONSize() {
        InterviewerService interviewerService = new InterviewerServiceImpl();
        return interviewerService.getInterviewerSize();
    }


    @RequestMapping(value = {"/mail-template"}, method = RequestMethod.GET)
    public String mail() {
        return "admin-mail-template";
    }


//    @RequestMapping(value = {"/scheduler"}, method = RequestMethod.GET)
//    @RequestMapping(value = {"/cesPost"}, method = RequestMethod.POST)
//    public @ResponseBody
//    CES getCES(@RequestBody CES ces) {
//        try {
//            cesService.setCES(ces);
//        } catch (DAOException e) {
//            e.printStackTrace();
//        }
//        return ces;
//    }


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


    //update from 12.05.2016
    @RequestMapping(value = {"/edit-form"}, method = RequestMethod.GET)
    public String editFormView() {
        return "edit-form";
    }

    @RequestMapping(value = {"/edit-form"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody List<Field> editFormGet(Integer ces_id) {
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

//    @RequestMapping(value = "/edit-form/delete-option", method = RequestMethod.POST, produces = "application/json")
//    public
//    @ResponseBody
//    Set<ValidationError> deleteOption(@RequestBody ListWrapper id) {
//        Validator validator = new DeleteQuestionValidator();
//        Set<ValidationError> errors = validator.validate(id);
//        if (errors.isEmpty()) {
//            System.out.println("Deal with it");
//        }
//        return errors;
//    }

    @RequestMapping(value = {"/enroll-session"}, method = RequestMethod.GET)
    public String enrollmentSessionView() {
        return "admin-es-view";
    }


}
