package ua.nc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.User;
import ua.nc.validator.ValidationError;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander on 05.05.2016.
 */
@Controller
public class SchedulerController {
    @RequestMapping(value = "/admin/scheduler", method = RequestMethod.POST,  produces = "application/json")
    public @ResponseBody
    Set<ValidationError> PostService(@RequestBody User Json) {
        Set<ValidationError> errors = new HashSet<>();
        System.out.println("Hello from Scheduler the value is:" +Json);
        return errors;
    }
}
