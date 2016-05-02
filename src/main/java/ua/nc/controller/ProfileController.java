package ua.nc.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.entity.profile.Profile;
import ua.nc.service.CESService;
import ua.nc.service.ProfileService;
import ua.nc.service.ProfileServiceImpl;
import ua.nc.service.UserDetailsImpl;
import ua.nc.validator.ProfileValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.util.Set;

/**
 * Created by Pavel on 28.04.2016.
 */
@Controller
public class ProfileController {
    private ProfileService profileService = new ProfileServiceImpl();
    private CESService cesService;

/*    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public
    @ResponseBody
    Profile profile() {
        return profileService.getProfile((UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal());
    }*/

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public
    @ResponseBody
    Set<ValidationError> profileFields(@RequestBody Profile profile) {
        Set<ValidationError> errors;
        Validator validator = new ProfileValidator();
        errors = validator.validate(profile);
        if (errors.isEmpty()) {
            profileService.setProfile((UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal(), profile);
        }
        return errors;
    }

    @RequestMapping(value = "/enroll", method = RequestMethod.GET)
    public String enroll() {
        if (cesService.getCurrentCES() != null) {
            cesService.enroll((UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal());
        }
        return "";
    }


}
