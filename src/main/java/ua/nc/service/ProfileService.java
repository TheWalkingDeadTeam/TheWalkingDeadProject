package ua.nc.service;


import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.Profile;

/**
 * Created by Pavel on 28.04.2016.
 */
public interface ProfileService {
    Profile getProfile(int userId, int cesID) throws DAOException;
    Profile getShortProfile(int userId, int cesId) throws DAOException;

    void setProfile(int userId, Profile profile) throws DAOException;
}
