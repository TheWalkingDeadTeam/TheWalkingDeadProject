package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.CES;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Rangar on 04.05.2016.
 */
public class PostgreCESDAO extends AbstractPostgreDAO<CES, Integer> implements CESDAO {
    private static final String GET_CURRENT_CES_QUERY = "SELECT ces.* from course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id AND stat.name != 'Closed'";
    private static final String GET_PENDING_CES_QUERY = "SELECT ces.* FROM course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id WHERE name = 'Pending'";
    private static final String GET_REGISTRATION_ONGOING_CES_QUERY = "SELECT ces.* FROM course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id WHERE name = 'RegistrationOngoing'";
    private static final String GET_POST_REGISTRATION_CES_QUERY = "SELECT ces.* FROM course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id WHERE name = 'PostRegistration'";
    private static final String GET_INTERVIEWING_ONGOING_CES_QUERY = "SELECT ces.* FROM course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id WHERE name = 'InterviewingOngoing'";
    private static final String GET_POST_INTERVIEWING_CES_QUERY = "SELECT ces.* FROM course_enrollment_session ces " +
            "JOIN ces_status stat ON ces.ces_status_id = stat.ces_status_id WHERE name = 'PostInterviewing'";

    private static final String ADD_INTERVIEWER_FOR_CURRENT_CES = "INSERT INTO interviewer_participation (ces_id, system_user_id) VALUES (?, ?);";
    private static final String addCESFieldQuery = "INSERT INTO ces_field (ces_id, field_id) VALUES (?, ?);";

    private static final String removeCESFieldQuery = "DELETE FROM ces_field WHERE ces_id = ? AND field_id = ?";
    private static final String removeInterviewerForCurrentCESQuery = "DELETE FROM interviewer_participation" +
            " WHERE ces_id = ? AND system_user_id = ?";

    public PostgreCESDAO(Connection connection) {
        super(connection);
    }

    private class PersistCES extends CES {
        public PersistCES(Integer year, Date startRegistrationDate, Integer quota, Integer reminders, Integer statusId,
                          Integer interviewTimeForPerson, Integer interviewTimeForDay) {
            super(year, startRegistrationDate, quota, reminders, statusId,
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
        return "UPDATE course_enrollment_session SET end_registration_date = ? ,start_interviewing_date = ?, " +
                "end_interviewing_date = ?, quota = ?, ces_status_id = ?, reminders = ?, interviewing_time_person = ?, " +
                "interviewing_time_day = ?, year = ? WHERE ces_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM course_enrollment_session";
    }



    @Override
    protected List<CES> parseResultSet(ResultSet rs) throws DAOException {
        List<CES> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistCES ces = new PersistCES(rs.getInt("year"), rs.getDate("start_registration_date"),
                        rs.getInt("quota"), rs.getInt("reminders"), rs.getInt("ces_status_id"),
                        rs.getInt("interviewing_time_person"), rs.getInt("interviewing_time_day"));
                ces.setId(rs.getInt("ces_id"));
                ces.setEndRegistrationDate(rs.getDate("end_registration_date"));
                ces.setStartInterviewingDate(rs.getDate("start_interviewing_date"));
                ces.setEndInterviewingDate(rs.getDate("end_interviewing_date"));
                result.add(ces);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, CES object) throws DAOException {
        try {
            statement.setInt(1, object.getYear());
            statement.setDate(2, new Date(object.getStartRegistrationDate().getTime()));
            if (object.getEndRegistrationDate() == null) {
                statement.setDate(3, null);
            } else {
                statement.setDate(3, new Date(object.getEndRegistrationDate().getTime()));
            }
            if (object.getStartInterviewingDate() == null) {
                statement.setDate(4, null);
            } else {
                statement.setDate(4, new Date(object.getStartInterviewingDate().getTime()));
            }
            if (object.getEndInterviewingDate() == null) {
                statement.setDate(5, null);
            } else {
                statement.setDate(5, new Date(object.getEndInterviewingDate().getTime()));
            }
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
            if (object.getEndRegistrationDate() == null) {
                statement.setDate(1, null);
            } else {
                statement.setDate(1, new Date(object.getEndRegistrationDate().getTime()));
            }
            if (object.getStartInterviewingDate() == null) {
                statement.setDate(2, null);
            } else {
                statement.setDate(2, new Date(object.getStartInterviewingDate().getTime()));
            }
            if (object.getEndInterviewingDate() == null) {
                statement.setDate(3, null);
            } else {
                statement.setDate(3, new Date(object.getEndInterviewingDate().getTime()));
            }
            statement.setInt(4, object.getQuota());
            statement.setInt(5, object.getStatusId());
            statement.setInt(6, object.getReminders());
            statement.setInt(7, object.getInterviewTimeForPerson());
            statement.setInt(8, object.getInterviewTimeForDay());
            statement.setInt(9, object.getYear());
            statement.setInt(10, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public CES getCurrentCES() throws DAOException {
        return getSomeCES(GET_CURRENT_CES_QUERY);
    }

    @Override
    public CES getPendingCES() throws DAOException {
        return getSomeCES(GET_PENDING_CES_QUERY);
    }

    @Override
    public CES getCurrentInterviewBegunCES() throws DAOException {
        return null;
    }

    public CES getRegistrationOngoingCES() throws DAOException {
        return getSomeCES(GET_REGISTRATION_ONGOING_CES_QUERY);
    }

    public CES getPostRegistrationCES() throws DAOException {
        return getSomeCES(GET_POST_REGISTRATION_CES_QUERY);
    }

    public CES getInterviewingOngoingCES() throws DAOException {
        return getSomeCES(GET_INTERVIEWING_ONGOING_CES_QUERY);
    }

    public CES getPostInterviewingCES() throws DAOException {
        return getSomeCES(GET_POST_INTERVIEWING_CES_QUERY);
    }

    private CES getSomeCES(String query) throws DAOException {
        CES result;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                result = null;
            } else {
                result = parseResultSet(rs).iterator().next();
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    private void doSmthWithCESFieldOrInterviewerParticipation(String query, int cesId, int otherId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cesId);
            statement.setInt(2, otherId);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On change modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void addCESField(int cesId, int fieldId) throws DAOException {
        doSmthWithCESFieldOrInterviewerParticipation(addCESFieldQuery, cesId, fieldId);
    }

    @Override
    public void removeCESField(int cesId, int fieldId) throws DAOException {
        doSmthWithCESFieldOrInterviewerParticipation(removeCESFieldQuery, cesId, fieldId);
    }

    @Override
    public void addInterviewerForCurrentCES(int cesId, int interviewerId) throws DAOException {
        doSmthWithCESFieldOrInterviewerParticipation(ADD_INTERVIEWER_FOR_CURRENT_CES, cesId, interviewerId);
    }

    @Override
    public void removeInterviewerForCurrentCES(int cesId, int interviewerId) throws DAOException {
        doSmthWithCESFieldOrInterviewerParticipation(removeInterviewerForCurrentCESQuery, cesId, interviewerId);
    }

    @Override
    public CES create(CES object) throws DAOException {
        return persist(object);
    }
}
