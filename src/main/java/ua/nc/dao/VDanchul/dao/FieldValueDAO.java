package ua.nc.dao.VDanchul.dao;

import ua.nc.dao.VDanchul.entities.FieldValue;

import java.util.List;

/**
 * Created by Rangar on 27.04.2016.
 */
public interface FieldValueDAO {
    public List<FieldValue> getUserFieldValuesForCurrentCES(int userID);

    public FieldValue create();

    public void update(FieldValue fieldValue);
}
