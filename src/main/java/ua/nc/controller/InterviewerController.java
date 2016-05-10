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
    private IntervieweeService intervieweeService = new IntervieweeServiceImpl();

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(){
        return "profile-for-interviewer";
    }

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.POST, produces = "application/json")
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
    @RequestMapping(value = "getFeedback/{id}", method = RequestMethod.POST, produces = "application/json")
    public Feedback getFeedback(@PathVariable("id") Integer id){
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        if (userService.checkRole(user,"ROLE_DEV")){
            return feedbackService.getFeedback(interviewee.getDevFeedbackID());
        } else {
            return feedbackService.getFeedback(interviewee.getHrFeedbackID());
        }
    }
}
