package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.FeedbackDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 03.05.2016.
 */
public class PostgreFeedbackDAO extends AbstractPostgreDAO<Feedback, Integer> implements FeedbackDAO {
    public PostgreFeedbackDAO(Connection connection) {
        super(connection);
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
                feedback.setId(rs.getInt("feedback_id"));
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
            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Feedback getById(int id) throws DAOException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            statement = connection.prepareStatement(getSelectQuery());
            statement.setInt(1,id);
            resultSet = statement.executeQuery();
            List<Feedback> feedbacks = parseResultSet(resultSet);
            return feedbacks.size() < 1 ? null : feedbacks.get(0);
        } catch (SQLException ex){
            throw new DAOException(ex);
        }
    }

    @Override
    public Feedback create(Feedback object) throws DAOException {
        return persist(object);
    }

    private class PersistFeedback extends Feedback {
        public PersistFeedback(int score, String comment, int interviewerID) {
            super(score, comment, interviewerID);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }
}
