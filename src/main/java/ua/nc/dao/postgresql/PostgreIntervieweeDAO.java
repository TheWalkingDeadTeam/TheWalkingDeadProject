package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.IntervieweeDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Interviewee;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rangar on 03.05.2016.
 */
public class PostgreIntervieweeDAO extends AbstractPostgreDAO<Interviewee, Integer> implements IntervieweeDAO {
    public PostgreIntervieweeDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM interviewee WHERE application_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO interviewee (application_id, interview_time) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE interviewee SET interview_time = ?, special_mark = ?, " +
                "dev_feedback_id = ?, hr_feedback_id = ? WHERE application_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM interviewee";
    }

    @Override
    protected List<Interviewee> parseResultSet(ResultSet rs) throws DAOException {
        List<Interviewee> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Interviewee interviewee = new Interviewee(rs.getInt("application_id"),rs.getTimestamp("interview_time"));
                interviewee.setSpecialMark(rs.getString("special_mark"));
                interviewee.setDevFeedbackID((Integer) rs.getObject("dev_feedback_id"));
                interviewee.setHrFeedbackID((Integer) rs.getObject("hr_feedback_id"));
                result.add(interviewee);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Interviewee object) throws DAOException {
        try {
            statement.setInt(1, object.getId());
            statement.setTimestamp(2, new Timestamp(object.getInterviewTime().getTime()));
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Interviewee object) throws DAOException {
        try {
            statement.setObject(1, object.getInterviewTime());
            statement.setString(2, object.getSpecialMark());
            statement.setObject(3, object.getDevFeedbackID());
            statement.setObject(4, object.getHrFeedbackID());
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Interviewee create(Interviewee object) throws DAOException {
        return persist(object);
    }
}
