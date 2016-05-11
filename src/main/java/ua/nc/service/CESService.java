package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

/**
 * Created by Pavel on 03.05.2016.
 */
public interface CESService {
    CES getCurrentCES();

    void enroll(Integer userId, Integer currentCESId) throws DAOException;

    CES getCES() throws DAOException;

    void setCES(CES ces) throws DAOException;

    void deleteCES();
}
