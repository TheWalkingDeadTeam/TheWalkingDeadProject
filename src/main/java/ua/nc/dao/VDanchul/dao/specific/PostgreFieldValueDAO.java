package ua.nc.dao.VDanchul.dao.specific;

import ua.nc.dao.VDanchul.dao.AbstractPostgreDAO;
import ua.nc.dao.VDanchul.dao.FieldValueDAO;
import ua.nc.dao.VDanchul.entities.FieldValue;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
