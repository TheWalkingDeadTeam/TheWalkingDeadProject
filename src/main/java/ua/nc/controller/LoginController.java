package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.User;
import ua.nc.service.*;
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
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private final UserService userService = new UserServiceImpl();
    private final PhotoService photoService = new PhotoServiceImpl();
    private final CESService cesService = new CESServiceImpl();


    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

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
    public String login(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "admin";
        } else {
            if (request.isUserInRole("ROLE_HR") || request.isUserInRole("ROLE_DEV") || request.isUserInRole("ROLE_BA") || request.isUserInRole("ROLE_STUDENT")) {
                return "account";
            } else {
                return "login";
            }
        }
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
            LOGGER.info("Sign in successful with email " + user.getEmail());
        } catch (BadCredentialsException e) {
            LOGGER.warn("Authorization deny " + user.getEmail() + " has another password");
            errors.add(new ValidationError("signin", "Invalid username or password"));
        } catch (UsernameNotFoundException e) {
            LOGGER.warn("Authorization deny email" + user.getEmail() + " not found");
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
                    LOGGER.warn("Register failed " + user.getEmail());
                    errors.add(new ValidationError("register", "Register failed"));
                }
            } else {
                LOGGER.warn("User " + user.getEmail() + " already exists");
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
                User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getUsername());
                photoService.uploadPhoto(photo, user.getId());
            } catch (IOException e) {
                errors.add(new ValidationError("photo", "Something went wrong"));
            }
        }
        return errors;
    }

    @RequestMapping(value = {"/passwordRecovery"}, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Set<ValidationError> recoverPassword(@RequestBody User user) {
        Validator validator = new RegistrationValidator();
        Set<ValidationError> errors = validator.validate(user);
        UserService userService = new UserServiceImpl();
        User updatedUser = userService.recoverPass(user);
        if (errors.isEmpty()) {
            if (updatedUser == null || updatedUser.getEmail() == null
                    && user.getPassword() == null) {
                LOGGER.warn("Password recovery failed " + user.getEmail());
                errors.add(new ValidationError("password", "Recovery failed"));
            }
        }
        return errors;
    }


//    @RequestMapping(value = "/stuff/{stuffId}", method = RequestMethod.GET)
//    public ResponseEntity<InputStreamResource> downloadStuff(@PathVariable int stuffId)
//            throws IOException {
//        String fullPath = stuffService.figureOutFileNameFor(stuffId);
//        File file = new File(fullPath);
//
//        HttpHeaders respHeaders = new HttpHeaders();
//        respHeaders.setContentType("application/pdf");
//        respHeaders.setContentLength(12345678);
//        respHeaders.setContentDispositionFormData("attachment", "fileNameIwant.pdf");
//
//        InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
//        return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
//    }


    @ResponseBody
    @RequestMapping(value = "/getPhoto")
    public byte[] getPhoto() {
        User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUsername());
        return photoService.getPhotoById(user.getId());
    }
    @RequestMapping(value = {"/cesPost"}, method = RequestMethod.POST)
    public @ResponseBody
    CES getCES(@RequestBody CES ces) {
        try {
            cesService.setCES(ces);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return ces;
    }


    @RequestMapping(value = {"/cessettings"}, method = RequestMethod.GET)
    public String cesPage() {
        return "cessettings";
    }

    @RequestMapping(value = "/cessettings", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    CES ces() {
        try {
            return cesService.getCES();
        } catch (DAOException e) {
            LOGGER.error("DAO error");
            return null;
        }
    }

}
