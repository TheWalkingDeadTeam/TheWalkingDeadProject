package ua.nc.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.Profile;
import ua.nc.service.CESService;
import ua.nc.service.ProfileService;
import ua.nc.service.ProfileServiceImpl;
import ua.nc.service.UserDetailsImpl;
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
    private ProfileService profileService = new ProfileServiceImpl();
    private CESService cesService;

//  @RequestMapping(value = "/profile", method = RequestMethod.GET)
//    public
//    @ResponseBody
//    Profile profile() {
//        return profileService.getProfile((UserDetailsImpl) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal());
//    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Profile profile(@PathVariable("id") Integer id) {
        System.out.println("Test");
        Profile profile = null;
        try {
            profile = profileService.getProfile(id, 1);
        } catch (DAOException e) {
            e.printStackTrace();
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
        errors = new LinkedHashSet<>();/*validator.validate(profile);*/
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
