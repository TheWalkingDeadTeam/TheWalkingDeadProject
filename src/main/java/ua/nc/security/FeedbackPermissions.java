package ua.nc.security;

import ua.nc.entity.CES;
import ua.nc.service.CESServiceImpl;

import java.util.Date;

/**
 * Created by Hlib on 14.05.2016.
 */
public class FeedbackPermissions {
    public boolean isInterviewingPeriod(){
        CES ces = new CESServiceImpl().getCurrentCES();
        Date today = new Date();
        return today.after(ces.getStartInterviewingDate()) && today.before(ces.getEndInterviewingDate());
    }

    public boolean isAfterInterviewingPeriod(){
        CES ces = new CESServiceImpl().getCurrentCES();
        Date today = new Date();
        return today.after(ces.getEndInterviewingDate());
    }
}
