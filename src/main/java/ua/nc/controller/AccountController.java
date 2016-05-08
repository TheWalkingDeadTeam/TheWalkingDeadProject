package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nc.service.UserDetailsImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Neltarion on 04.05.2016.
 */
@Controller
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(AccountController.class);

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account() {
        return "account";
    }

    @RequestMapping(value = "/account/profile", method = RequestMethod.GET)
    public String profileTest(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_STUDENT")) {
            Integer id = ((UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal()).getId();
            return "redirect:/profile?" + id;
        }
        return "account";
    }

}
