package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ua.nc.entity.User;
import ua.nc.service.PhotoService;
import ua.nc.service.PhotoServiceImpl;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserDetailsServiceImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.PhotoValidator;
import ua.nc.validator.RegistrationValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class LoginController implements HandlerExceptionResolver {
    private final Logger log = Logger.getLogger(LoginController.class);
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    private final UserService userService = new UserServiceImpl();
    private final PhotoService photoService = new PhotoServiceImpl();

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof MaxUploadSizeExceededException) {
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            Set<ValidationError> errors = new LinkedHashSet<>();
            errors.add(new ValidationError("photo", "File is too big"));
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/security_check ", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> authentication(@RequestBody User user) {
        Set<ValidationError> errors = new LinkedHashSet<>();
        UserDetailsService userDetailsService = new UserDetailsServiceImpl();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        try {
            token.setDetails(userDetailsService.loadUserByUsername(user.getEmail()));
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("Sign in successful with email " + user.getEmail());
        } catch (BadCredentialsException e) {
            log.warn("Authorization deny " + user.getEmail() + " has another password");
            errors.add(new ValidationError("signin", "Invalid username or password"));
        } catch (UsernameNotFoundException e) {
            log.warn("Authorization deny email" + user.getEmail() + " not found");
            errors.add(new ValidationError("signin", "Invalid username or password"));
        }
        return errors;
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> registerUser(@RequestBody User user) {
        Validator validator = new RegistrationValidator();
        Set<ValidationError> errors = validator.validate(user);
        if (errors.isEmpty()) {
            if (userService.getUser(user.getEmail()) == null) {
                User registeredUser = userService.createUser(user);
                if (registeredUser == null) {
                    log.warn("Register failed " + user.getEmail());
                    errors.add(new ValidationError("register", "Register failed"));
                }
            } else {
                log.warn("User " + user.getEmail() + " already exists");
                errors.add(new ValidationError("user", "Such user already exists"));
            }
        }
        return errors;
    }

    @RequestMapping(value = {"/uploadPhoto"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        Validator validator = new PhotoValidator();
        Set<ValidationError> errors = validator.validate(photo);
        if (errors.isEmpty()) {
            try {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String usernameName = userDetails.getUsername();
                User user = userService.getUser(usernameName);
                photoService.uploadPhoto(photo, user.getId());
            } catch (IOException e) {
                errors.add(new ValidationError("photo", "Something went wrong"));
            }
        }
        for (ValidationError error : errors) {
            System.out.println(error.getErrorMessage());
        }
        return errors;
    }

    @ResponseBody
    @RequestMapping(value = "/getPhoto")
    public byte[] getPhoto() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String usernameName = userDetails.getUsername();
        User user = userService.getUser(usernameName);
        return photoService.getPhotoById(user.getId());
    }

}
