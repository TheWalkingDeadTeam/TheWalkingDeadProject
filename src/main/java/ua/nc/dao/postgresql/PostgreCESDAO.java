package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

import java.sql.Connection;
import java.sql.Date;
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
        public PersistCES(Integer year, Calendar startRegistrationDate, Calendar endRegistrationDate,Integer quota,
                          Integer reminders, Integer statusId, Integer interviewTimeForPerson, Integer interviewTimeForDay) {
            super(year, startRegistrationDate, endRegistrationDate,quota, reminders, statusId,
                    interviewTimeForPerson, interviewTimeForDay);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM course_enrollment_session WHERE ces_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO course_enrollment_session (year, start_registration_date, end_registration_date," +
                "start_interviewing_date, end_interviewing_date, quota, ces_status_id, reminders, " +
                "interviewing_time_person, interviewing_time_day) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE course_enrollment_session SET start_interviewing_date = ?, end_interviewing_date = ?, " +
                "quota = ?, reminders = ?, interviewing_time_person = ?, interviewing_time_day = ? WHERE ces_id = ?;";
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
        try {
            statement.setInt(1, object.getYear());
            statement.setDate(2, (Date) object.getStartRegistrationDate().getTime());
            statement.setDate(3, (Date) object.getEndRegistrationDate().getTime());
            statement.setDate(4, (Date) object.getStartInterviewingDate().getTime());
            statement.setDate(5, (Date) object.getEndInterviewingDate().getTime());
            statement.setInt(6, object.getQuota());
            statement.setInt(7, object.getStatusId());
            statement.setInt(8, object.getReminders());
            statement.setInt(9, object.getInterviewTimeForPerson());
            statement.setInt(10, object.getInterviewTimeForDay());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, CES object) throws DAOException {
        try {
            statement.setDate(1, (Date) object.getStartInterviewingDate().getTime());
            statement.setDate(2, (Date) object.getEndInterviewingDate().getTime());
            statement.setInt(3, object.getQuota());
            statement.setInt(4, object.getReminders());
            statement.setInt(5, object.getInterviewTimeForPerson());
            statement.setInt(6, object.getInterviewTimeForDay());
            statement.setInt(7, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, CES object) throws DAOException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private static final String getCurrentCESQuery = "SELECT ces.CES_ID from CES ces JOIN ON CES_Status stat " +
            "WHERE ces.CES_Status_ID = stat.CES_Status_ID AND stat.Name = 'Active'";

    @Override
    public CES getCurrentCES() throws DAOException {
        CES result;
        try (PreparedStatement statement = connection.prepareStatement(getCurrentCESQuery)) {
            result = parseResultSet(statement.executeQuery()).iterator().next();
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
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
