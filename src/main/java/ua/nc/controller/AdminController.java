package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class AdminController {
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String login(Model model) {
        return "/WEB-INF/jsp/admin.jsp";
    }

}
