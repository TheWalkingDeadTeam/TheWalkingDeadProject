package ua.nc.service;

import ua.nc.entity.User;

/**
 * Created by Pavel on 18.04.2016.
 */
public interface UserService {
    User getUser(String email);
}
