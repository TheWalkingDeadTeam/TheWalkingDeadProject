package ua.nc.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;
import ua.nc.service.CESService;
import ua.nc.service.EnrollOnInterviewingServiceImpl;
import ua.nc.service.UserDetailsImpl;

/**
 * Created by Yaroslav on 09.05.2016.
 */
@Controller
public class EnrollInterviewerController {
    private static final Logger LOGGER = Logger.getLogger(EnrollInterviewerController.class);
    private CESService cesService = new EnrollOnInterviewingServiceImpl();
    private String result = null ;
    @RequestMapping(value = "/interviewer", method = RequestMethod.GET)
    public @ResponseBody String enroll() {
        CES currentCES = cesService.getCurrentCES();
        if (currentCES != null) {
            try {
                cesService.enroll(((UserDetailsImpl) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()).getId(), currentCES.getId());
                result = "You has been successfully enrolled to current CES";
            } catch (DAOException e) {
                result = "Can't enroll to current CES.";
                LOGGER.info("");
            }
        } else {
            result = "Can't enroll to current CES. Current CES session is not exist";
            LOGGER.info("Can't enroll to current CES. Current CES session is not exist");
        }
        return result;
    }
}
