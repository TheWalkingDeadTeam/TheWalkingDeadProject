package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nc.entity.Application;
import ua.nc.entity.Feedback;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.ValidationError;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Hlib on 09.05.2016.
 */

@Controller
@RequestMapping("/interviewer")
public class InterviewerController {
    private final static Logger LOGGER = Logger.getLogger(InterviewerController.class);
    private ApplicationService applicationService = new ApplicationServiceImpl();
    private UserService userService = new UserServiceImpl();
    private FeedbackService feedbackService = new FeedbackServiceImpl();

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(){
        return "profile-for-interviewer";
    }

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.POST, produces = "application/json")
    public Application specificFeedback(@PathVariable("id") Integer id){
        return applicationService.getApplicationByUserForCurrentCES(id);
    }

    @RequestMapping(value = "feedback/{id}/save", method = RequestMethod.POST, produces = "application/json")
    public Set<ValidationError> saveFeedback(@RequestBody Feedback feedback, @PathVariable("id") Integer id){
        Set<ValidationError> errors = new LinkedHashSet<>();
        int interviewerID = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername()).getId();
        feedback.setInterviewerID(interviewerID);
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        feedbackService.saveFeedback(feedback, application);
        return  errors;
    }
}
