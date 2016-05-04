package ua.nc.service;

import ua.nc.entity.ces.CES;

/**
 * Created by Ермоленко on 02.05.2016.
 */
public interface CESService {
    CES getCurrentCES();

    void setCES(CES ces);
}