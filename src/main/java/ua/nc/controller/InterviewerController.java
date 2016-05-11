package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.entity.Application;
import ua.nc.entity.Feedback;
import ua.nc.entity.Interviewee;
import ua.nc.entity.User;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.FeedbackValidator;
import ua.nc.validator.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Hlib on 09.05.2016.
 */

@Controller
@RequestMapping("/interviewer")
public class InterviewerController {
    private final static Logger LOGGER = Logger.getLogger(InterviewerController.class);
    private final static String ROLE_DEV = "ROLE_DEV";
    private ApplicationService applicationService = new ApplicationServiceImpl();
    private UserService userService = new UserServiceImpl();
    private FeedbackService feedbackService = new FeedbackServiceImpl();
    private IntervieweeService intervieweeService = new IntervieweeServiceImpl();

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(){
        return "profile-for-interviewer";
    }

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET, produces = "application/json")
    public Application specificFeedback(@PathVariable("id") Integer id){
        return applicationService.getApplicationByUserForCurrentCES(id);
    }

    @ResponseBody
    @RequestMapping(value = "feedback/{id}/save", method = RequestMethod.POST, produces = "application/json")
    public Set<ValidationError> saveFeedback(@RequestBody Feedback feedback, @PathVariable("id") Integer id){
        Set<ValidationError> errors = new FeedbackValidator().validate(feedback);
        int interviewerID = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername()).getId();
        feedback.setInterviewerID(interviewerID);
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        if  (!feedbackService.saveFeedback(feedback, application)){
            errors.add(new ValidationError("save", "Unable to save feedback"));
        }
        return  errors;
    }

    @ResponseBody
    @RequestMapping(value = "getFeedback/{id}", method = RequestMethod.GET, produces = "application/json")
    public Feedback getFeedback(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        Feedback feedback = null;
        Integer feedbackId = null;
        if(request.isUserInRole(ROLE_DEV)){
            feedbackId = interviewee.getDevFeedbackID();

        } else {
            feedbackId = interviewee.getHrFeedbackID();
        }
        if (feedbackId != null) {
            feedback = feedbackService.getFeedback(feedbackId);
        }
        if (feedback == null) {
            response.setHeader("restricted", "false");
            try {
                Writer writer = response.getWriter();
                writer.write("null");
                writer.close();
            } catch (IOException ex){
                LOGGER.warn(ex);
            }
            return null;
        } else if (feedback.getInterviewerID() == user.getId()) {
            response.setHeader("restricted", "false");
            return feedback;
        } else {
            response.setHeader("restricted", "true");
        }
        try {
            Writer writer = response.getWriter();
            writer.write("null");
            writer.close();
        } catch (IOException ex){
            LOGGER.warn(ex);
        }
        return null;
    }
}
