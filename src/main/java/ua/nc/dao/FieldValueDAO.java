package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.FieldValue;

import java.util.List;

/**
 * Created by Rangar on 01.05.2016.
 */
public interface FieldValueDAO {
    List<FieldValue> getFieldValueByUserCESField(Integer user_id, Integer ces_id, Integer field_id) throws DAOException;
    void update(FieldValue fieldValue);
    FieldValue create(FieldValue fieldValue);
    void deleteMultiple(Integer user_id, Integer ces_id, Integer field_id);
}
