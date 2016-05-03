package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.ListValue;

import java.util.List;

/**
 * Created by Rangar on 01.05.2016.
 */
public interface ListValueDAO extends GenericDAO<ListValue, Integer> {
    List<ListValue> getAllListListValue(Integer list_id) throws DAOException;
}

