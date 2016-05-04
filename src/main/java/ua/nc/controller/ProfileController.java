package ua.nc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ua.nc.entity.profile.Profile;
import ua.nc.service.ProfileService;
import ua.nc.validator.ProfileValidator;
import ua.nc.validator.ValidationError;
import ua.nc.validator.Validator;

import java.util.Set;

/**
 * Created by Pavel on 28.04.2016.
 */
@Controller
public class ProfileController {
    private ProfileService profileService;

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

//    @RequestMapping(value="/profile/#{id}", method = RequestMethod.GET)
//    public @ResponseBody Profile getProfileById (@ModelAttribute Integer id) {
////        return profileService.getProfileById(id);
//    }


    @RequestMapping(value="/profile/#{id}", method = RequestMethod.GET)
    public String getProfileById (@PathVariable(value = "id") String  id) {
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
            profileService.setProfile(profile);
        }
        return errors;
    }


}
