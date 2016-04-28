package ua.nc.dao.postgresql.profile;

import ua.nc.dao.FieldValueDAO;
import ua.nc.entity.profile.FieldValue;

import java.util.List;

/**
 * Created by Rangar on 26.04.2016.
 */
public class PostgreFieldValueDAO implements FieldValueDAO {

    @Override
    public List<FieldValue> getUserFieldValuesForCurrentCES(int userID) {
        String sql = "";
        return null;
    }

    @Override
    public FieldValue create() {
        return null;
    }

    @Override
    public void update(FieldValue fieldValue) {

    }
}
