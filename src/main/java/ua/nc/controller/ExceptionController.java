package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Pavel on 18.04.2016.
 */
@ControllerAdvice
@Controller
public class ExceptionController {

    @RequestMapping(value = {"/error", "/403"}, method = RequestMethod.GET)
    public String login() {
        return "error";
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404()   {
        return "/error-404";
    }


}
