package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Mail;

import java.util.List;

/**
 * Created by Alexander Haliy on 23.04.2016.
 */
public interface MailDAO {
    List<Mail> getByHeader(String header) throws DAOException;

    Mail create(Mail entity) throws DAOException;

    Mail get(Integer id) throws DAOException;

    Mail update(Mail entity) throws DAOException;

    void delete(Mail entity) throws DAOException;

    List<Mail> getAll() throws DAOException;
}