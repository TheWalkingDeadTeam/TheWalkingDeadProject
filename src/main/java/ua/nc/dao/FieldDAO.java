package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.Field;

import java.util.List;

/**
 * Created by Rangar on 01.05.2016.
 */
public interface FieldDAO extends GenericDAO<Field, Integer> {
    List<Field> getFieldsForCES(Integer ces_id) throws DAOException;
}
