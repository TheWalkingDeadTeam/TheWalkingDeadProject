package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.enums.UserRoles;
import ua.nc.entity.Role;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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

    @RequestMapping(value = "/account/{id}", method = RequestMethod.GET)
    public User account(@PathVariable("id") Integer id, HttpServletRequest request) {
        if (request.isUserInRole(UserRoles.ROLE_STUDENT.name())){
            Integer userId = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUsername()).getId();
            if (!id.equals(userId)) return null;
        }
        User user = userService.findUserById(id);
        return user;
    }

    @RequestMapping(value = "/account/profile", method = RequestMethod.GET)
    public String profileTest(HttpServletRequest request) {
        if (request.isUserInRole(UserRoles.ROLE_STUDENT.name())) {
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
    public User getUser(SecurityContextHolderAwareRequestWrapper request) {
        User user = null;
        if (request.getRemoteUser() != null){
            user = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUsername());
        }
        return user;
    }

    @ResponseBody
    @RequestMapping(value = "/account/changeroles", method = RequestMethod.POST)
    public void changeRoles(@RequestBody User user) {
        System.out.println(user);
        String email = user.getEmail();
        Set<Role> roles = user.getRoles();
        System.out.println(email);
        System.out.println(roles);
        for (Role role : roles) {
            System.out.println(role.getName());
        }
        userService.changeRoles(email, roles);
        System.out.println("!!!");
    }

    @ResponseBody
    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") Integer id, SecurityContextHolderAwareRequestWrapper request) {
        User user = null;
        if (request.isUserInRole(UserRoles.ROLE_ADMIN.name())
                || (((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getId().equals(id))) {
            user = userService.findUserById(id);
        }
        return user;
    }
}
