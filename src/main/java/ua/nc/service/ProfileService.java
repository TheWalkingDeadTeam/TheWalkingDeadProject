package ua.nc.service;


import ua.nc.entity.profile.Profile;

/**
 * Created by Pavel on 28.04.2016.
 */
public interface ProfileService {
    Profile getProfile(UserDetailsImpl userDetails);
    void setProfile(Profile profile);
    void createProfile(Profile profile);
}
