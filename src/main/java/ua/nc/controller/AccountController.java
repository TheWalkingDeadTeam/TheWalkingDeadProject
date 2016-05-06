package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

/**
 * Created by Neltarion on 04.05.2016.
 */
@Controller
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(AccountController.class);

    UserService userService = new UserServiceImpl();

    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUser() {
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        return user;
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account() {
        return "account";
    }
}
