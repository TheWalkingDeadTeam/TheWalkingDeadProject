package ua.nc.service;


import ua.nc.entity.profile.FieldValue;
import ua.nc.entity.profile.Profile;

import java.util.List;

/**
 * Created by Pavel on 28.04.2016.
 */
public interface ProfileService {
    Profile getProfile(UserDetailsImpl userDetails, int cesID);
    void setProfile(UserDetailsImpl userDetails, int cesID, Profile profile);
}
