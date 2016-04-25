package ua.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsServiceImpl;
import ua.nc.service.UserService;
import ua.nc.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class LoginController implements HandlerExceptionResolver {
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof MaxUploadSizeExceededException){
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
    public
    @ResponseBody
    JSONResponse authentication(@RequestBody User user) {
        JSONResponse jsonResponse = new JSONResponse();
        UserDetailsService userDetailsService = new UserDetailsServiceImpl();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        token.setDetails(userDetailsService.loadUserByUsername(user.getEmail()));
        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("success");
            jsonResponse.setRedirect("/login");
        } catch (BadCredentialsException e) {
            System.out.println("Authorization deny" + user.getEmail());
            jsonResponse.setRedirect("/login?error");
        }
        return jsonResponse;
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    JSONResponse registerUser(@RequestBody User user) {
        JSONResponse jsonResponse = new JSONResponse();
        if (userService.getUser(user.getEmail()) == null) {
            User registredUser = userService.createUser(user);
            if (registredUser != null) {
                jsonResponse.setRedirect("/login?register=success");
            } else {
                jsonResponse.setRedirect("/login?register=failed");
            }
        } else {
            System.out.println("User exist");
            jsonResponse.setRedirect("/login?register=exist");
        }
        return jsonResponse;
    }

    @RequestMapping(value = {"/uploadPhoto"}, method = RequestMethod.POST, produces = "application/json")
    public
    String uploadPhoto(@RequestParam("photo") MultipartFile photo){
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
                     return"redirect:/login?photo=exception";
                }
            }
        return "redirect:/login?photo=success";
    }

    private class JSONResponse {
        private String redirect;

        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }
    }

}
