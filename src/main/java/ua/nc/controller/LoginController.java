package ua.nc.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ua.nc.dao.enums.UserRoles;
import ua.nc.entity.User;
import ua.nc.service.PhotoService;
import ua.nc.service.PhotoServiceImpl;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserDetailsServiceImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.*;

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
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private final UserService userService = new UserServiceImpl();
    private final PhotoService photoService = new PhotoServiceImpl();
    private final UserDetailsService userDetailsService = new UserDetailsServiceImpl();


    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login(SecurityContextHolderAwareRequestWrapper request, HttpServletResponse response) {
        SavedRequest savedRequest =
                new HttpSessionRequestCache().getRequest(request, response);
        if (savedRequest != null  && request.getUserPrincipal() != null) {
            LOGGER.info("Login and redirect to " + savedRequest.getRedirectUrl());
            return "redirect:" + savedRequest.getRedirectUrl();
        } else {
            if (request.isUserInRole(UserRoles.ROLE_ADMIN.name())
                    || request.isUserInRole(UserRoles.ROLE_HR.name())) {
                LOGGER.info("Login and redirect to Admin page");
                return "admin";

            } else {
                if (request.isUserInRole(UserRoles.ROLE_BA.name())
                        || request.isUserInRole(UserRoles.ROLE_DEV.name())
                        || request.isUserInRole(UserRoles.ROLE_STUDENT.name())) {
                    LOGGER.info("Login and redirect to Account page");
                    return "account";
                }
            }
        }
        return "login";
    }


    @ResponseBody
    @RequestMapping(value = "/security_check ", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ValidationError> authentication(@RequestBody User user) {
        Set<ValidationError> errors = new AuthenticationValidator().validate(user);
        if (errors.isEmpty()) {
            try {
                AbstractAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
                token.setDetails(userDetailsService.loadUserByUsername(user.getEmail()));
                Authentication auth = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                LOGGER.info("Sign in successful with email " + user.getEmail());
            } catch (BadCredentialsException e) {
                LOGGER.warn("Authorization deny " + user.getEmail() + " has another password ");
                errors.add(new ValidationError("signin", "Invalid username or password"));
            }
        }
        return errors;
    }

    /**
     * This method create new user of the system and validate his information
     * If user with user.email not exist, create new user.
     *
     * @param user the user that want to register
     * @return json of errors that were created during registration
     */
    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    Set<ValidationError> registerUser(@RequestBody User user) {
        Set<ValidationError> errors =  new RegistrationValidator().validate(user);
        if (errors.isEmpty()) {
            User registeredUser = userService.createUser(user);
            if (registeredUser == null) {
                LOGGER.warn("Register failed " + user.getEmail());
                errors.add(new ValidationError("register", "Register failed"));
            } else {
                LOGGER.info("Register successfully " + user.getEmail());
            }

        }
        return errors;
    }


    @RequestMapping(value = {"/passwordRecovery"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    Set<ValidationError> recoverPassword(@RequestBody String email) {
        Validator validator = new EmailValidator();
        Set<ValidationError> errors = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode node = objectMapper.readValue(email, JsonNode.class);
            JsonNode emailNode = node.get("email");
            email = emailNode.asText();
            errors = validator.validate(email);
            if (errors.isEmpty()) {

                User user = userService.findUserByEmail(email);
                if (user != null) {
                    userService.recoverPass(user);
                } else {
                    LOGGER.warn("Recovery failed " + email);
                    errors.add(new ValidationError("passwordRecovery", "Recovery failed"));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to parse", e);
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
                User user = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getUsername());
                photoService.uploadPhoto(photo, user.getId());
            } catch (IOException e) {
                errors.add(new ValidationError("photo", "Something went wrong"));
            }
        }
        return errors;
    }


    @ResponseBody
    @RequestMapping(value = "/getPhoto")
    public byte[] getPhoto() {
        User user = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        return photoService.getPhotoById(user.getId());
    }


    @ResponseBody
    @RequestMapping(value = "/getPhoto/{id}")
    public byte[] getPhoto(@PathVariable("id") Integer id, HttpServletRequest request) {
        if (request.isUserInRole(UserRoles.ROLE_STUDENT.name())) {
            Integer userId = userService.findUserByEmail(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUsername()).getId();
            if (!id.equals(userId)) return null;
        }
        return photoService.getPhotoById(id);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof MaxUploadSizeExceededException) {
            ModelAndView modelAndView = new ModelAndView();
            httpServletResponse.setHeader("exception", "File is too big");
            Set<ValidationError> errors = new LinkedHashSet<>();
            errors.add(new ValidationError("photo", "File is too big"));
            modelAndView.addObject("errors", errors);
            return modelAndView;
        }
        return new ModelAndView("redirect:/login");
    }

    /*@ExceptionHandler(MaxUploadSizeExceededException.class)
    public Set<ValidationError> resolveException(HttpServletRequest request){
        request.setAttribute("exception", "File is too big");
        Set<ValidationError> errors = new LinkedHashSet<>();
        errors.add(new ValidationError("photo", "File is too big"));
        return errors;
    }*/
}
