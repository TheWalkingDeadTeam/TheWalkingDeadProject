package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Neltarion on 04.05.2016.
 */
@Controller
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(AccountController.class);
    private static final UserService userService = new UserServiceImpl();

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


    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser() {
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        return user;
    }
}
