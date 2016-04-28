package ua.nc.service;

import ua.nc.entity.Profile;
import ua.nc.entity.User;

/**
 * Created by Pavel on 28.04.2016.
 */
public interface ProfileService {
    Profile getProfile(UserDetailsImpl userDetails);
    void setProfile(Profile profile);
    void createProfile(Profile profile);
}
