package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nc.entity.User;
import ua.nc.service.UserService;
import ua.nc.service.UserServiceImpl;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class LoginController {
    private final UserService userService = new UserServiceImpl();

    @ModelAttribute("user")
    public User constructUser() {
        return new User();
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user) {
        if (userService.getUser(user.getEmail()) == null) {
            User registredUser = userService.createUser(user);
            if (registredUser != null) {
                return "redirect:/login?register=success";
            } else {
                return "redirect:/login?register=failed";
            }
        } else {
            return "redirect:/login?register=exist";
        }
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testCommand(Model model) {
        System.out.println("Before");
        userService.findUsersByName("Alex");
        System.out.println("After");
        userService.delete(35);
        userService.findUsersByName("Alex");
        return "test";
    }

}
