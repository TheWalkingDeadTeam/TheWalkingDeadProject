package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Mail;

import java.util.List;

/**
 * Created by Alexander Haliy on 23.04.2016.
 */
public abstract class MailDAO extends GenericDAO<Mail, Integer> {
    public abstract List<Mail> getByHeader(String header) throws DAOException;
}
