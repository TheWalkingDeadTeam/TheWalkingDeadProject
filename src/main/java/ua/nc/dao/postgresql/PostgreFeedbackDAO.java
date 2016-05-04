package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 03.05.2016.
 */
public class PostgreFeedbackDAO extends AbstractPostgreDAO<Feedback, Integer> {
    public  PostgreFeedbackDAO(Connection connection){
        super(connection);
    }

    private class PersistFeedback extends Feedback{
        public PersistFeedback(int score, String comment, int interviewerID) {
            super(score, comment, interviewerID);
        }

        public void setID(int id) {
            super.setID(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM feedback WHERE feedback_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO feedback (score, comment, interviewer_id) VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE feedback SET score = ?, comment = ?, interviewer_id = ? WHERE feedback_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM feedback";
    }

    @Override
    protected List<Feedback> parseResultSet(ResultSet rs) throws DAOException {
        List<Feedback> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistFeedback feedback = new PersistFeedback(rs.getInt("score"),
                        rs.getString("comment"), rs.getInt("interviewer_id"));
                feedback.setID(rs.getInt("feedback_id"));
                result.add(feedback);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Feedback object) throws DAOException {
        try {
            statement.setInt(1, object.getScore());
            statement.setString(2, object.getComment());
            statement.setInt(3, object.getInterviewerID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Feedback object) throws DAOException {
        try {
            statement.setInt(1, object.getScore());
            statement.setString(2, object.getComment());
            statement.setInt(3, object.getInterviewerID());
            statement.setInt(4, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, Feedback object) throws DAOException {
        try {
            statement.setInt(1, object.getID());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Feedback create(Feedback object) throws DAOException {
        return persist(object);
    }
}