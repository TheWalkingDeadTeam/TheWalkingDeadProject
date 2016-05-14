package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.*;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.FeedbackValidator;
import ua.nc.validator.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;
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
    private CESService cesService = new CESServiceImpl();

    @RequestMapping(value = "/enroll", method = RequestMethod.GET)
    public @ResponseBody String enroll() {
        CES currentCES = cesService.getCurrentCES();
        if (currentCES != null) {
            try {
                cesService.enrollAsInterviewer(((UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()).getId(), currentCES.getId());
                return "You has been successfully enrolled to current CES as interviewer";
            } catch (DAOException e) {
                LOGGER.info("");
                return "Can't enroll to current CES as interviewer.";
            }
        } else {
            LOGGER.info("Can't enroll to current CES. Current CES session is not exist");
            return  "Can't enroll to current CES. Current CES session is not exist";
        }
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(){
        return "profile-for-interviewer";
    }

    @RequestMapping(value = "/feedback/{id}", method = RequestMethod.GET, produces = "application/json")
    public Application specificFeedback(@PathVariable("id") Integer id, HttpServletResponse response){
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        if (application == null){
           fillNullResponse(response);
        }
        return application;
    }

    @PreAuthorize("@feedbackPermissions.isInterviewingPeriod()")
    @ResponseBody
    @RequestMapping(value = "feedback/{id}/save", method = RequestMethod.POST, produces = "application/json")
    public Set<ValidationError> saveFeedback(@RequestBody FeedbackAndSpecialMark feedbackAndSpecialMark, @PathVariable("id") Integer id, HttpServletRequest request){
        Feedback feedback = feedbackAndSpecialMark.getFeedback();
        Set<ValidationError> errors = new FeedbackValidator().validate(feedback);
        int interviewerID = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername()).getId();
        feedback.setInterviewerID(interviewerID);
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
        Integer feedbackId = request.isUserInRole("ROLE_DEV") ? interviewee.getDevFeedbackID() : interviewee.getHrFeedbackID();
        boolean restricted = feedbackId != null && feedbackService.getFeedback(feedbackId).getId().equals(interviewerID);
        if (!restricted) {
            if (!feedbackService.saveFeedback(feedbackAndSpecialMark, application)) {
                errors.add(new ValidationError("save", "Unable to save feedback"));
            }
        } else {
            errors.add(new ValidationError("save", "You are not allowed to rate this student"));
        }
        return  errors;
    }

    @ResponseBody
    @RequestMapping(value = "getFeedback/{id}", method = RequestMethod.GET, produces = "application/json")
    public FeedbackAndSpecialMark getFeedback(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response){
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        if(application == null){
            response.setHeader("interviewee", "null");
            fillNullResponse(response);
            return null;
        }
        Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
        if (interviewee == null){
            response.setHeader("interviewee", "application");
            fillNullResponse(response);
            return null;
        }
        response.setHeader("interviewee", "interviewee");
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        FeedbackAndSpecialMark feedbackAndSpecialMark = new FeedbackAndSpecialMark();
        Feedback feedback = null;
        Integer feedbackId = null;
        if(request.isUserInRole("ROLE_DEV")){
            feedbackId = interviewee.getDevFeedbackID();

        } else {
            feedbackId = interviewee.getHrFeedbackID();
        }
        if (feedbackId != null) {
            feedback = feedbackService.getFeedback(feedbackId);
        }
        if (feedback == null) {
            response.setHeader("restricted", "false");
            feedbackAndSpecialMark.setSpecialMark(interviewee.getSpecialMark());
            fillNullResponse(response);
            return feedbackAndSpecialMark;
        } else if (feedback.getInterviewerID() == user.getId()) {
            response.setHeader("restricted", "false");
            feedbackAndSpecialMark.setFeedback(feedback);
            feedbackAndSpecialMark.setSpecialMark(interviewee.getSpecialMark());
            return feedbackAndSpecialMark;
        } else {
            response.setHeader("restricted", "true");
        }
        fillNullResponse(response);
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "getallfeedbacks/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<FeedbackAndSpecialMark> getAllFeedbacks(@PathVariable("id") Integer id)
    {
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
        List<FeedbackAndSpecialMark> feedbackAndSpecialMarks = new LinkedList<>();
        FeedbackAndSpecialMark feedbackAndSpecialMark = new FeedbackAndSpecialMark();
        feedbackAndSpecialMark.setSpecialMark(interviewee.getSpecialMark());
        feedbackAndSpecialMark.setFeedback(feedbackService.getFeedback(interviewee.getDevFeedbackID()));
        feedbackAndSpecialMarks.add(feedbackAndSpecialMark);
        feedbackAndSpecialMark = new FeedbackAndSpecialMark();
        feedbackAndSpecialMark.setFeedback(feedbackService.getFeedback(interviewee.getHrFeedbackID()));
        feedbackAndSpecialMarks.add(feedbackAndSpecialMark);
        return feedbackAndSpecialMarks;
    }

    private void fillNullResponse(HttpServletResponse response){
        try {
            Writer writer = response.getWriter();
            writer.write("null");
            writer.close();
        } catch (IOException ex){
            LOGGER.warn(ex);
        }
    }
}
