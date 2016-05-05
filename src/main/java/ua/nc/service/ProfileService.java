package ua.nc.service;


import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.FieldValue;
import ua.nc.entity.profile.Profile;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Pavel on 28.04.2016.
 */
public interface ProfileService {
    Profile getProfile(UserDetailsImpl userDetails, int cesId) throws DAOException;
    void setProfile(UserDetailsImpl userDetails, Profile profile) throws DAOException;
}
