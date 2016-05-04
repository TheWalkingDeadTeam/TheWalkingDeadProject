package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.ces.CES;
import ua.nc.service.*;


/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class AdminController {
    private final CESService cesService = new CESServiceImpl();
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String login() {
        return "admin";
    }

    @RequestMapping(value = {"/cesGet"}, method = RequestMethod.POST)
    public @ResponseBody CES getCES(@RequestBody CES ces) {
        return ces;
    }
    @RequestMapping(value = {"/cesSettings"}, method = RequestMethod.GET)
    public String cesPage() {
        return "cesSettings";
    }

    @RequestMapping(value = {"/cesSubmit"}, method = RequestMethod.GET)
    public String setCES(@RequestBody CES ces) {
        cesService.setCES(ces);
        return null;
    }


}
