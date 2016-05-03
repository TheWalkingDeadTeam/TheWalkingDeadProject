package ua.nc.service;

import ua.nc.entity.CES;

/**
 * Created by Pavel on 03.05.2016.
 */
public interface CESService {
    CES getCurrentCES();
    void enroll(UserDetailsImpl userDetails);
}
