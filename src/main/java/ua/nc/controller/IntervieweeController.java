package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.entity.IntervieweeRow;
import ua.nc.entity.Status;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

import java.util.List;

/**
 * Created by creed on 24.05.16.
 */
@Controller
public class IntervieweeController {

    private static final Logger LOGGER = Logger.getLogger(AdminController.class);
    private final UserService userService = new UserServiceImpl();
    private final CESService cesService = new CESServiceImpl();
    private final StudentService studentService = new StudentServiceImpl();
    private final InterviewerService interviewerService = new InterviewerServiceImpl();
    private final IntervieweeService intervieweeService = new IntervieweeServiceImpl();
    private final MailService mailService = new MailServiceImpl();

    @RequestMapping(value = {"/interviewee"}, method = RequestMethod.GET)
    public String intervieweeView() {
        return "interviewee-table";
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
}
