package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Rangar on 04.05.2016.
 */
public class PostgreCESDAO extends AbstractPostgreDAO<CES, Integer> implements CESDAO{
    public  PostgreCESDAO(Connection connection){
        super(connection);
    }

    private class PersistCES extends CES{
        public PersistCES(Integer year, Calendar startRegistrationDate, Integer quota, Integer reminders, Integer statusId,
                          Integer interviewTimeForPerson, Integer interviewTimeForDay) {
            super(year, startRegistrationDate, quota, reminders, statusId, interviewTimeForPerson, interviewTimeForDay);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM course_enrollment_session WHERE ces_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "";
    }

    @Override
    public String getUpdateQuery() {
        return "";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM course_enrollment_session";
    }

    @Override
    protected List<CES> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CES object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CES object) throws DAOException {

    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, CES object) throws DAOException {

    }

    @Override
    public CES getCurrentCES() {
        return null;
    }

    @Override
    public void addCESField(int cesId, int fieldId) {

    }

    @Override
    public void addInterviewerForCurrentCES(int interviewerId) {

    }

    @Override
    public CES create(CES object) throws DAOException {
        return persist(object);
    }
}
