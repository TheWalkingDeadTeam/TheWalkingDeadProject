package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Neltarion on 05.05.2016.
 */
@Controller
public class ContactsController {

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public String contacts() {
        return "contacts";
    }

}
