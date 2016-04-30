package ua.nc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
import ua.nc.entity.User;
import ua.nc.service.user.UserDetailsServiceImpl;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;
import ua.nc.validator.PasswordValidator;
import ua.nc.validator.RegistrationValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof MaxUploadSizeExceededException) {
            return new ModelAndView("redirect:/login?photo=toobig");
        }
        return new ModelAndView("redirect:/login");
    }

    private final UserService userService = new UserServiceImpl();

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/security_check ", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public JSONResponse authentication(@RequestBody User user) {
        JSONResponse jsonResponse = new JSONResponse();
        Set<ValidationError> errors = new LinkedHashSet<>();
        jsonResponse.setErrors(errors);
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
        return jsonResponse;
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    JSONResponse registerUser(@RequestBody User user) {
        JSONResponse jsonResponse = new JSONResponse();
        Validator validator = new RegistrationValidator();
        Set<ValidationError> errors = validator.validate(user);
        jsonResponse.setErrors(errors);
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
        return jsonResponse;
    }

    @RequestMapping(value = {"/uploadPhoto"}, method = RequestMethod.POST)
    public String uploadPhoto(@RequestParam("photo") MultipartFile photo) {
        if (!photo.isEmpty()) {
            if (!(photo.getOriginalFilename().endsWith(".jpg") || photo.getOriginalFilename().endsWith(".jpeg") ||
                    photo.getOriginalFilename().endsWith(".png"))) {
                return "redirect:/login?photo=wrong";
            }
            try {
                byte[] photoBytes = photo.getBytes();

                String resHome = System.getProperty("catalina.home");
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                String userName = userDetails.getUsername();
                int userID = userService.getUser(userName).getId();
                File dir = new File(resHome + File.separator + "photos" + File.separator + userID);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File localFile = new File(dir.getAbsolutePath() + File.separator + "photo");
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(localFile));
                stream.write(photoBytes);
                stream.close();
            } catch (IOException e) {
                return "redirect:/login?photo=exception";
            }
        }
        return "redirect:/login?photo=success";
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







    private class JSONResponse {
        private Set<ValidationError> errors;

        public Set<ValidationError> getErrors() {
            return errors;
        }

        public void setErrors(Set<ValidationError> errors) {
            this.errors = errors;
        }
    }


}