package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nc.entity.Application;

/**
 * Created by Hlib on 09.05.2016.
 */

@Controller
@RequestMapping(value = "/interviewer")
public class InterviewerController {
    private final static Logger LOGGER = Logger.getLogger(InterviewerController.class);

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String feedback(){
        return "feedback";
    }

    @RequestMapping(value = "/feedback/{id}")
    public Application specificFeedback(@PathVariable("id") Integer id){
        return null;
    }
}
