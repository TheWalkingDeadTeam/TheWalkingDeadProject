package ua.nc.service;

import ua.nc.entity.Application;
import ua.nc.entity.User;

/**
 * Created by Hlib on 09.05.2016.
 */
public interface ApplicationService {
    Application getApplicationByUserForCurrentCES(User user);
}
