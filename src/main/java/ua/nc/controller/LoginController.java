package ua.nc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsServiceImpl;
import ua.nc.service.UserService;
import ua.nc.service.UserServiceImpl;

/**
 * Created by Pavel on 18.04.2016.
 */
@Controller
public class LoginController {
    @Autowired
    @Qualifier("authenticationManager")
    protected AuthenticationManager authenticationManager;

    private final UserService userService = new UserServiceImpl();

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/security_check ", method = RequestMethod.POST, produces = "application/json" )
    public @ResponseBody JSONResponse authentication(@RequestBody User user) {
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
            System.out.println("Duthorization deny" + user.getEmail());
            jsonResponse.setRedirect("/login?error");
        }
        return jsonResponse;
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST, produces="application/json")
    public @ResponseBody JSONResponse registerUser(@RequestBody User user) {
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
