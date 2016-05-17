package ua.nc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.PasswordValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.io.IOException;
import java.util.Set;


/**
 * Created by Alexander on 30.04.2016.
 */
@Controller
public class UserController {
    private final UserService userService = new UserServiceImpl();
    private final Logger log = Logger.getLogger(UserController.class);

    /**
     * Registered user can change password
     *
     * @param password
     * @return
     */
    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Set<ValidationError> changePassword(@RequestBody String password) {
        Validator validator = new PasswordValidator();
        Set<ValidationError> errors = null;
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readValue(password, JsonNode.class);
            JsonNode passwordNode = node.get("password");
            password = passwordNode.asText();
            errors = validator.validate(password);
        } catch (IOException e) {
            log.error("Failed to parse", e);
        }
        if (errors.isEmpty()) {
            userService.changePassword(user, password);
        } else {
            log.warn("User  pass word can't be changed ");
            errors.add(new ValidationError("userPassword", "Pass change error"));
        }
        return errors;
    }
}



