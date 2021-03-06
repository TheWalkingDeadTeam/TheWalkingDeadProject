package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.enums.UserRoles;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;
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
    private PhotoService photoService = new PhotoServiceImpl();

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    Profile profile(@PathVariable("id") Integer id, SecurityContextHolderAwareRequestWrapper request) {
        Profile profile = null;
        EditFormService efs = new EditFormServiceImpl();
        if (request.isUserInRole(UserRoles.ROLE_ADMIN.name())
                || request.isUserInRole(UserRoles.ROLE_HR.name())
                || request.isUserInRole(UserRoles.ROLE_BA.name())
                || request.isUserInRole(UserRoles.ROLE_DEV.name())
                || ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getId().equals(id)) {
            try {
                LOGGER.info("Try to get profile " + id);
                profile = profileService.getProfile(id, efs.getCES_ID());
            } catch (DAOException e) {
                LOGGER.error("Cant get profile " ,e);
            }
        }
        return profile;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profileTest() {
        return "profile";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST, produces = "application/json")
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
                LOGGER.error(e);
            }
        }
        return errors;
    }

    @RequestMapping(value = "/profile/enroll", method = RequestMethod.GET)
    public
    @ResponseBody
    Set<ValidationError> enroll() {
        Set<ValidationError> errors = new LinkedHashSet<>();
        CES currentCES = cesService.getCurrentCES();
        if (currentCES != null) {
            try {
                int userId = ((UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()).getId();
                if (photoService.getPhotoById(userId) == null){
                    errors.add(new ValidationError("enrollAsStudent", "Photo not specified"));
                }
                if (errors.isEmpty()) {
                    cesService.enrollAsStudent(((UserDetailsImpl) SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getPrincipal()).getId(), currentCES.getId());
                }
            } catch (DAOException e) {
                errors.add(new ValidationError("enrollAsStudent", "You have already enrolled to current CES"));
                LOGGER.info("You have already enrolled to current CES", e.getCause());
            }
        } else {
            errors.add(new ValidationError("enrollAsStudent", "Can't enrollAsStudent to current CES. Current CES session is not exist"));
            LOGGER.info("Can't enrollAsStudent to current CES. Current CES session is not exist");
        }
        return errors;
    }
}
