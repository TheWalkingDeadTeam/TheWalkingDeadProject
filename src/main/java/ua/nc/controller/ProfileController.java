package ua.nc.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.dao.CESDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.Profile;
import ua.nc.service.CESService;
import ua.nc.service.ProfileService;
import ua.nc.service.ProfileServiceImpl;
import ua.nc.service.UserDetailsImpl;
import ua.nc.validator.ProfileValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.sql.Connection;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Created by Pavel on 28.04.2016.
 */
@Controller
public class ProfileController {
    private ProfileService profileService = new ProfileServiceImpl();
    private CESService cesService;
    private final static Logger LOGGER = Logger.getLogger(ProfileController.class);

    @RequestMapping(value = "/getProfile", method = RequestMethod.GET)
    public
    @ResponseBody
    Profile getProfile() {
        /*Connection connection =
        CESDAO cesdao =*/
        try {
            return profileService.getProfile((UserDetailsImpl) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal(), 1);
        } catch (DAOException ex){
            LOGGER.warn(ex.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    @ResponseBody
    public Set<ValidationError> profileFields(@RequestBody Profile profile) {
        Set<ValidationError> errors;
        Validator validator = new ProfileValidator();
        errors = validator.validate(profile);
        if (errors.isEmpty()) {
            try {
                profileService.setProfile(((UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()).getId(), profile);
            } catch (DAOException e) {
                e.printStackTrace(); //toDO add log
            }
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
