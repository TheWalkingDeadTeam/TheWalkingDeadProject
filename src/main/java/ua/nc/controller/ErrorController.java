package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class ErrorController {
    @RequestMapping(value = {"/error", "/403"}, method = RequestMethod.GET)
    public String login() {
        return "error";
    }

}
