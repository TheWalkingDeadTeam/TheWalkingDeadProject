package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.profile.Profile;
import ua.nc.service.*;
import ua.nc.validator.ProfileValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Pavel on 28.04.2016.
 */
@Controller
public class ProfileController {
    private static final Logger LOGGER = Logger.getLogger(ProfileController.class);
    private ProfileService profileService = new ProfileServiceImpl();
    private CESService cesService = new CESServiceImpl();

/*    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Profile profile(@PathVariable Integer) {
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

    @RequestMapping(value = "profile/enroll", method = RequestMethod.GET)
    public @ResponseBody Set<ValidationError> enroll() {
        Set<ValidationError> errors = new LinkedHashSet<>();
        CES currentCES = cesService.getCurrentCES();
        if (currentCES != null) {
            try {
                cesService.enroll(((UserDetailsImpl)SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()).getId(), currentCES.getId());
            } catch (DAOException e) {
                LOGGER.info("");
            }
        } else {
            LOGGER.info("Can't enroll to current CES. Current CES session is not exist");
        }
        return errors;
    }


}
