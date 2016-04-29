package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Mail;

import java.util.List;

/**
 * Created by Alexander Haliy on 23.04.2016.
 */
public abstract class MailDAO  {
    public abstract List<Mail> getByHeader(String header) throws DAOException;

    public abstract Mail create(Mail entity) throws DAOException;

    public abstract Mail get(Integer id) throws DAOException;

    public abstract Mail update(Mail entity) throws DAOException;

    public abstract void delete(Mail entity) throws DAOException;
}
