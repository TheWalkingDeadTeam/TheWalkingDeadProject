package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class AdminController {
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String login() {
        return "admin";
    }

    /**
     * Method for view student list from admin controle panale
     * UC 7
     * @return page with students data
     */
    @RequestMapping(value = {"/students"}, method = RequestMethod.GET)
    public String studentsView() {

        return "adminStudView";
    }
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public
    @ResponseBody
    void profileFields() {
        System.out.println("URAAA");

    }

}
