package ua.nc.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.nc.dao.enums.UserStatus;
import ua.nc.entity.User;
import ua.nc.service.UserDetailsImpl;
import ua.nc.service.user.UserDetailsServiceImpl;

import java.applet.AppletContext;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Pavel on 30.05.2016.
 */

@Component
public class AuthenticationValidator implements Validator {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationValidator.class);
    private final UserDetailsService userDetailsService = new UserDetailsServiceImpl();

    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new LinkedHashSet<>();
        User user = (User) obj;
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(user.getEmail());
            if (userDetails.getStatus() != UserStatus.Active) {
                errors.add(new ValidationError("signin", "Authorization deny. Account is not active"));
                LOGGER.warn("Authorization deny email " + userDetails.getUsername() + " . Account status is not Active");
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.warn("Authorization deny email" + user.getEmail() + " not found", e);
            errors.add(new ValidationError("signin", "Invalid username or password"));
        }
        return errors;
    }
}
