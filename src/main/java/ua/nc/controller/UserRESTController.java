package ua.nc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.nc.entity.User;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Kryvonis on 4/25/16.
 */
@Controller
@RequestMapping(value = {"/users"})
public class UserRESTController {


    @RequestMapping(value = {"/{id}"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@PathVariable("id") long id, BindingResult result, HttpServletResponse response) throws BindException {

        System.out.println("UserPOST0");
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        System.out.println(id);

//        response.setHeader("Location", "/users/" + id);
        User user = new User();
        return user;
    }

}
