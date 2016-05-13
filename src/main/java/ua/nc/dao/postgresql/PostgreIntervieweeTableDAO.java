package ua.nc.dao.postgresql;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Interviewee;
import ua.nc.entity.IntervieweeRow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by Rangar on 13.05.2016.
 */
public class PostgreIntervieweeTableDAO {
    private Connection connection;

    public PostgreIntervieweeTableDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String[] SET_VALUES = new String[]{"system_user_id", "name", "surname", "special_mark", "dev_score", "hr_score"};
    private static final Set<String> FIELDS = new HashSet<>(Arrays.asList(SET_VALUES));

    private static final String GET_INTERVIEWEE_TABLE_QUERY = "SELECT system_user.system_user_id, system_user.name, system_user.surname, " +
            "interviewee.special_mark, dev_feedback.score as dev_score, hr_feedback.score as hr_score  " +
            "FROM interviewee  " +
            "LEFT JOIN feedback AS dev_feedback ON dev_feedback_id = dev_feedback.feedback_id  " +
            "LEFT JOIN feedback AS hr_feedback ON hr_feedback_id = hr_feedback.feedback_id  " +
            "JOIN application ON interviewee.application_id = application.application_id " +
            "JOIN system_user ON application.system_user_id = system_user.system_user_id " +
            "WHERE (application.ces_id = ?) " +
            "AND (system_user.name LIKE ? OR system_user.surname LIKE ? OR interviewee.special_mark LIKE ?) " +
            "ORDER BY {0} {1} " +
            "LIMIT ? OFFSET ?";

    private static final String SORT_QUERY = "SELECT interviewee.*, dev_feedback.score+hr_feedback.score as score " +
            "            FROM interviewee   " +
            "            JOIN feedback AS dev_feedback ON dev_feedback_id = dev_feedback.feedback_id   " +
            "            JOIN feedback AS hr_feedback ON hr_feedback_id = hr_feedback.feedback_id   " +
            "            JOIN application ON interviewee.application_id = application.application_id  " +
            "            WHERE (application.ces_id = ?)  " +
            "            ORDER BY score";

    private static final String GET_INTERVIEWEE_TABLE_COUNT_QUERY = "SELECT count(*) " +
            "            FROM interviewee     " +
            "            JOIN application ON interviewee.application_id = application.application_id  " +
            "            JOIN system_user ON application.system_user_id = system_user.system_user_id  " +
            "            WHERE (application.ces_id = ?) " +
            "            AND (system_user.name LIKE ? OR system_user.surname LIKE ? OR interviewee.special_mark LIKE ?)";

    private String buildQuery(String orderBy, Boolean asc) throws DAOException {
        if (FIELDS.contains(orderBy)){
            String order = asc ? "ASC" : "DESC";
            return MessageFormat.format(GET_INTERVIEWEE_TABLE_QUERY, orderBy, order);
        } else {
            throw new DAOException("Nice try, but injection isn't allowed");
        }
    }

    public List<IntervieweeRow> getIntervieweeTable(Integer cesId) throws DAOException {
        return  getIntervieweeRows(cesId, null, null, "system_user_id", "", true);
    }

    public List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset) throws DAOException {
        return getIntervieweeRows(cesId, limit, offset, "system_user_id", "", true);
    }

    public List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy) throws DAOException {
        return getIntervieweeRows(cesId, limit, offset, orderBy, "", true);
    }

    public List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy, Boolean asc) throws DAOException {
        return getIntervieweeRows(cesId, limit, offset, orderBy, "", asc);
    }

    public List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern) throws DAOException {
        return getIntervieweeRows(cesId, limit, offset, orderBy, pattern, true);
    }

    public List<IntervieweeRow> getIntervieweeTable(Integer cesId, Integer limit, Integer offset, String orderBy ,String pattern, Boolean asc) throws DAOException {
        return getIntervieweeRows(cesId, limit, offset, orderBy, pattern, asc);
    }

    private List<IntervieweeRow> getIntervieweeRows(Integer cesId, Integer limit, Integer offset, String orderBy , String pattern, Boolean asc) throws DAOException{
        String fullQuery = buildQuery(orderBy, asc);
        try (PreparedStatement statement = this.connection.prepareStatement(fullQuery)) {
            statement.setInt(1, cesId);
            statement.setString(2, "%" + pattern + "%");
            statement.setString(3, "%" + pattern + "%");
            statement.setString(4, "%" + pattern + "%");
            statement.setObject(5, limit);
            statement.setObject(6, offset);
            return parseResultSet(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private List<IntervieweeRow> parseResultSet(ResultSet rs) throws SQLException {
        List<IntervieweeRow> result = new LinkedList<>();
        while (rs.next()){
            IntervieweeRow intervieweeRow = new IntervieweeRow();
            intervieweeRow.id = rs.getInt("system_user_id");
            intervieweeRow.name = rs.getString("name");
            intervieweeRow.surname = rs.getString("surname");
            intervieweeRow.special_mark = rs.getString("special_mark");
            intervieweeRow.dev_score = rs.getString("dev_score");
            intervieweeRow.hr_score = rs.getString("hr_score");
            if (intervieweeRow.special_mark != null) {
                switch (intervieweeRow.special_mark) {
                    case JOB_OFFER:
                        intervieweeRow.color = COLORS.get(JOB_OFFER);
                        break;
                    case TAKE_ON_COURSES:
                        intervieweeRow.color = COLORS.get(TAKE_ON_COURSES);
                        break;
                    case REJECTED:
                        intervieweeRow.color = COLORS.get(REJECTED);
                        break;
                    case GET_ON_COURSES:
                        intervieweeRow.color = COLORS.get(GET_ON_COURSES);
                        break;
                }
            } else {
                intervieweeRow.color = COLORS.get("");
            }
            result.add(intervieweeRow);
        }
        return result;
    }

    private static final Map<String, Integer> COLORS = new HashMap<String, Integer>(){
        {
            put(JOB_OFFER, 1);
            put(TAKE_ON_COURSES, 2);
            put(GET_ON_COURSES, 2);
            put(REJECTED, 3);
            put("", 4);
        }
    };
    private static final String JOB_OFFER = "job offer";
    private static final String TAKE_ON_COURSES = "take on courses";
    private static final String REJECTED = "rejected";
    private static final String[] MARKS = new String[]{JOB_OFFER, TAKE_ON_COURSES, REJECTED};
    private static final Set<String> SPECIAL_MARKS = new HashSet<>(Arrays.asList(MARKS));
    private static final String GET_ON_COURSES = "get on courses";

    public void updateIntervieweeTable(Integer cesId, Integer quota) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(SORT_QUERY)) {
            statement.setInt(1, cesId);
            PostgreIntervieweeDAO subDAO = new PostgreIntervieweeDAO(connection);
            List<Interviewee> interviewees = subDAO.parseResultSet(statement.executeQuery());
            int counter = 0;
            for (Interviewee interviewee : interviewees){
                if (!SPECIAL_MARKS.contains(interviewee.getSpecialMark())){
                    counter++;
                    if (counter <= quota) {
                        interviewee.setSpecialMark(GET_ON_COURSES);
                    } else {
                        interviewee.setSpecialMark(null);
                    }
                }
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Integer getIntervieweeCount(Integer cesId, String pattern) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(GET_INTERVIEWEE_TABLE_COUNT_QUERY)) {
            statement.setInt(1, cesId);
            statement.setString(2, "%" + pattern + "%");
            statement.setString(3, "%" + pattern + "%");
            statement.setString(4, "%" + pattern + "%");
            return getCount(statement.executeQuery());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private Integer getCount(ResultSet rs) throws SQLException {
        Integer result = 0;
        while (rs.next()) {
            result = rs.getInt("count");
        }
        return result;
    }
}
