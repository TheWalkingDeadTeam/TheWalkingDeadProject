package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.nc.dao.enums.UserRoles;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.*;
import ua.nc.service.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.FeedbackValidator;
import ua.nc.validator.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

    @RequestMapping(value = "/enroll-ces-interviewer", method = RequestMethod.POST)
    public
    @ResponseBody
    HttpStatus enroll(@RequestBody IntegerList integerList) {
        CES currentCES = cesService.getCurrentCES();
        if (currentCES != null) {
            int cesId = cesService.getCurrentCES().getId();
            for (Integer userId :integerList.getValues() ) {
                try {
                    cesService.enrollAsInterviewer(userId, cesId);
                    LOGGER.info("Successfully enrolled on current CES");
                } catch (DAOException e) {
                    LOGGER.info("Cant enroll on current CES", e);
                }
            }
        } else {
            LOGGER.info("Can't enroll to current CES. Current CES session is not exist");
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback() {
        return "profile-for-interviewer";
    }

    @ResponseBody
    @RequestMapping(value = "feedback/{id}/save", method = RequestMethod.POST, produces = "application/json")
    public Set<ValidationError> saveFeedback(@RequestBody FeedbackAndSpecialMark feedbackAndSpecialMark, @PathVariable("id") Integer id, HttpServletRequest request) {
        Feedback feedback = feedbackAndSpecialMark.getFeedback();
        Set<ValidationError> errors = new FeedbackValidator().validate(feedback);
        int interviewerID = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername()).getId();
        feedback.setInterviewerID(interviewerID);
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
        Integer feedbackId = request.isUserInRole("ROLE_DEV") ? interviewee.getDevFeedbackID() : interviewee.getHrFeedbackID();
        boolean restricted = feedbackId != null && feedbackService.getFeedback(feedbackId).getInterviewerID() != interviewerID;
        if (!restricted) {
            if (!feedbackService.saveFeedback(feedbackAndSpecialMark, application)) {
                errors.add(new ValidationError("save", "Unable to save feedback"));
            }
        } else {
            errors.add(new ValidationError("save", "You are not allowed to rate this student"));
        }
        return errors;
    }

    @ResponseBody
    @RequestMapping(value = "getFeedback/{id}", method = RequestMethod.GET, produces = "application/json")
    public FeedbackDTO getFeedback(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        Application application = applicationService.getApplicationByUserForCurrentCES(id);
        FeedbackDTO feedbackDTO = new FeedbackDTO();
        if (request.isUserInRole(UserRoles.ROLE_DEV.name())){
            feedbackDTO.setInterviewerRole(UserRoles.ROLE_DEV);
        } else if (request.isUserInRole(UserRoles.ROLE_BA.name())){
            feedbackDTO.setInterviewerRole(UserRoles.ROLE_BA);
        } else if (request.isUserInRole(UserRoles.ROLE_HR.name())){
            feedbackDTO.setInterviewerRole(UserRoles.ROLE_HR);
        } else {
            return null;
        }
        Date today = new Date();
        CES currentCES = cesService.getCurrentCES();
        if (today.after(currentCES.getStartInterviewingDate())&&today.before(currentCES.getEndInterviewingDate())) {
            feedbackDTO.setInterviewingPeriod();
            if (application == null) {
                feedbackDTO.setApplicationExists(false);
                return feedbackDTO;
            } else {
                feedbackDTO.setApplicationExists(true);
            }
            Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
            if (interviewee == null) {
                feedbackDTO.setIntervieweeExists(false);
                return feedbackDTO;
            } else {
                feedbackDTO.setIntervieweeExists(true);
            }
            User user = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUsername());
            if (!cesService.checkParticipation(user.getId())){
                feedbackDTO.setRestricted(true);
                return feedbackDTO;
            }
            Feedback feedback = null;
            Integer feedbackId = null;
            if (feedbackDTO.getInterviewerRole().equals(UserRoles.ROLE_DEV)) {
                feedbackId = interviewee.getDevFeedbackID();
            } else {
                feedbackId = interviewee.getHrFeedbackID();
            }
            if (feedbackId != null) {
                feedback = feedbackService.getFeedback(feedbackId);
            }
            if (feedback == null) {
                feedbackDTO.setRestricted(false);
                feedbackDTO.setSpecialMark(interviewee.getSpecialMark());
                return feedbackDTO;
            } else if (feedback.getInterviewerID() == user.getId()) {
                feedbackDTO.setRestricted(false);
                if (feedbackDTO.getInterviewerRole().equals(UserRoles.ROLE_DEV)) {
                    feedbackDTO.setDevFeedback(feedback);
                } else{
                    feedbackDTO.setHrFeedback(feedback);
                }
                feedbackDTO.setSpecialMark(interviewee.getSpecialMark());
                return feedbackDTO;
            } else {
                feedbackDTO.setRestricted(true);
                return feedbackDTO;
            }
        } else if (today.after(currentCES.getEndInterviewingDate())) {
            feedbackDTO.setAfterInterviewingPeriod();
            if (application == null) {
                feedbackDTO.setApplicationExists(false);
                return feedbackDTO;
            } else {
                feedbackDTO.setApplicationExists(true);
            }
            Interviewee interviewee = intervieweeService.getInterviewee(application.getId());
            if (interviewee == null) {
                feedbackDTO.setIntervieweeExists(false);
                return feedbackDTO;
            } else {
                feedbackDTO.setIntervieweeExists(true);
            }
            if (interviewee.getDevFeedbackID() != null) {
                feedbackDTO.setDevFeedback(feedbackService.getFeedback(interviewee.getDevFeedbackID()));
            }
            if (interviewee.getHrFeedbackID() != null) {
                feedbackDTO.setHrFeedback(feedbackService.getFeedback(interviewee.getHrFeedbackID()));
            }
            feedbackDTO.setSpecialMark(interviewee.getSpecialMark());
            return feedbackDTO;
        } else {
            feedbackDTO.setRestricted();
            return feedbackDTO;
        }
    }

    @RequestMapping(value = "/leave-feedback")
    public ModelAndView leaveFeedback(){
        return new ModelAndView("leave-feedback");
    }

    @RequestMapping(value = "/view-feedback")
    public ModelAndView viewFeedback(){
        return new ModelAndView("view-feedback");
    }
}
