package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.enums.UserStatus;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreUserDAO extends AbstractPostgreDAO<User, Integer> implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(PostgreUserDAO.class);
    private static final String SQL_UPDATE_USER = "UPDATE public.system_user SET password = ? WHERE system_user_id = ?";
    private final String GET_INTERVIEWERS_FOR_CURRENT_CES = "SELECT users.system_user_id, users.email, users.name, users.surname " +
            " FROM public.system_user users " +
            " JOIN public.interviewer_participation ip ON ip.system_user_id = users.system_user_id " +
            " WHERE ces_id = (SELECT ces.ces_id FROM public.course_enrollment_session ces JOIN public.ces_status stat " +
            " ON ces.ces_status_id = stat.ces_status_id AND stat.name != 'Closed')";
    private final String GET_STUDENTS_FOR_CURRENT_CES = "SELECT users.system_user_id, users.email, users.name, users.surname " +
            " FROM public.system_user users" +
            " JOIN public.application app ON app.system_user_id = users.system_user_id" +
            " WHERE ces_id = (SELECT ces.ces_id FROM public.course_enrollment_session ces JOIN public.ces_status stat" +
            " ON ces.ces_status_id = stat.ces_status_id AND stat.name = 'Active') AND app.rejected IS FALSE";
    private final String FIND_BY_EMAIL = "SELECT u.*, sus.name as status FROM public.system_user u" +
            " JOIN public.system_user_status sus ON u.system_user_status_id = sus.system_user_status_id WHERE u.email = ?";
    private final String CREATE_USER = "INSERT INTO public.system_user(name, surname, email, password, system_user_status_id) VALUES (?, ?, ?, ?, ?)";
    private final String SET_ROLE_TO_USER = "INSERT INTO public.system_user_role(role_id, system_user_id) SELECT ?, system_user_id FROM public.system_user u WHERE u.email = ?";
    private final String GET_BY_ROLE = "SELECT u.system_user_id, u.name, u.surname, u.email FROM public.system_user u " +
            " JOIN public.system_user_role ur ON u.system_user_id = ur.system_user_id" +
            " JOIN public.role r ON ur.role_id = r.role_id" +
            " WHERE r.name = ?";
    private final String GET_STATUS_ID = "SELECT system_user_status_id FROM system_user_status WHERE name = ?";
    private final String SELECT = "SELECT * FROM system_user u WHERE u.system_user_id = ?";
    private static final String CHANGE_USER_STATUS_QUERY = "UPDATE system_user SET system_user_status_id = ? WHERE system_user_id = ?;";

    public PostgreUserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public User findByEmail(String email) throws DAOException {
        String sql = FIND_BY_EMAIL;
        PersistUser user = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            user = new PersistUser();
            user.setId(resultSet.getInt("system_user_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setStatus(UserStatus.valueOf(resultSet.getString("status")));
            LOGGER.debug("Find user " + user.getName() + " byEmail");
        } catch (SQLException e) {
            LOGGER.info("User with " + email + " not found in DB" + e.getMessage());
            throw new DAOException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
        return user;
    }

    @Override
    public void createUser(User user) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(GET_STATUS_ID);
            statement.setString(1, "Active");
            resultSet = statement.executeQuery();
            resultSet.next();
            int statusId = resultSet.getInt("system_user_status_id");
            statement = connection.prepareStatement(CREATE_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setInt(5, statusId);
            statement.executeUpdate();
            if (!user.getRoles().isEmpty()) {
                statement = connection.prepareStatement(SET_ROLE_TO_USER);
                for (Role role : user.getRoles()) {
                    statement.setInt(1, role.getId());
                    statement.setString(2, user.getEmail());
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.warn("User with name" + user.getName() + "  not created", e.getCause());
            try {
                connection.rollback();
            } catch (SQLException eRoll) {
                LOGGER.warn("Unable to rollback transaction", eRoll.getCause());
                throw new DAOException(eRoll);
            }
            throw new DAOException(e);
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    @Override
    public Set<User> getByRole(Role role) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        PersistUser user = null;
        Set<User> users = new HashSet<>();
        try {
            statement = connection.prepareStatement(GET_BY_ROLE);
            statement.setString(1, role.getName());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new PersistUser();
                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
        return users;
    }

    @Override
    public Set<User> getStudentsForCurrentCES() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Set<User> users = new LinkedHashSet<>();
        try {
            statement = connection.prepareStatement(GET_STUDENTS_FOR_CURRENT_CES);
            resultSet = statement.executeQuery();
            PersistUser user = null;
            while (resultSet.next()) {
                user = new PersistUser();
                user.setId(resultSet.getInt("system_user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public Set<User> getInterviewersForCurrentCES() throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Set<User> users = new LinkedHashSet<>();
        try {
            statement = connection.prepareStatement(GET_INTERVIEWERS_FOR_CURRENT_CES);
            resultSet = statement.executeQuery();
            PersistUser user = null;
            while (resultSet.next()) {
                user = new PersistUser();
                user.setId(resultSet.getInt("system_user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public User create(User object) throws DAOException {
        return null;
    }

    @Override
    public String getSelectQuery() {
        return SELECT;
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getAllQuery() {
        return null;
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DAOException {
        List<User> users = new LinkedList<>();
        RoleDAO roleDAO = new PostgreRoleDAO(connection);
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getInt("system_user_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                Set<Role> roles = roleDAO.findByEmail(user.getEmail());
                user.setRoles(roles);
                users.add(user);
            }
        } catch (SQLException ex){
            throw new DAOException(ex);
        }
        return users;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws DAOException {

    }



    @Override
    public User persist(User object) throws DAOException {
        return null;
    }



    @Override
    public void update(User object) throws DAOException {

    }

    @Override
    public List<User> getAll() throws DAOException {
        return null;
    }

    /**
     * Updates user with new password
     *
     * @param user
     * @throws DAOException
     */
    @Override
    public void updateUser(User user) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("User:" + user.getName() + "  not updated");
            throw new DAOException(e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

    ////////////////////////////// VDanchul
    @Override
    public void activateUser(Integer id) throws DAOException {
        changeUserStatus(id, 1);
    }

    @Override
    public void deactivateUser(Integer id) throws DAOException {
        changeUserStatus(id, 2);
    }

    private void changeUserStatus(Integer id, Integer status) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(CHANGE_USER_STATUS_QUERY)) {
            statement.setInt(1, status);
            statement.setInt(2, id);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("On change modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private static final String GET_ALL_ACCREJ_STUDENTS_QUERY = "SELECT users.system_user_id, users.email, users.name, users.surname  " +
            "            FROM public.system_user users " +
            "            JOIN public.application app ON app.system_user_id = users.system_user_id " +
            "            WHERE ces_id = ? AND app.rejected = ?";

    @Override
    public Set<User> getAllAcceptedStudents(Integer cesId) throws DAOException {
        return getAllAccRejStudents(cesId, false);
    }

    @Override
    public Set<User> getAllRejectedStudents(Integer cesId) throws DAOException {
        return getAllAccRejStudents(cesId, true);
    }

    private Set<User> getAllAccRejStudents(Integer cesId, Boolean rejected) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ACCREJ_STUDENTS_QUERY)) {
            statement.setInt(1, cesId);
            statement.setBoolean(2, rejected);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                return getStudentsParse(rs);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private Set<User> getStudentsParse(ResultSet rs) throws DAOException {
        Set<User> users = new LinkedHashSet<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getInt("system_user_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                users.add(user);
            }
            return users;
        } catch (SQLException e){
            throw new DAOException(e);
        }
    }

    private static final String GET_SOME_INTERVIEWEE = "SELECT system_user.* FROM system_user  " +
            "JOIN application ON system_user.system_user_id = application.system_user_id " +
            "JOIN interviewee ON application.application_id = interviewee.application_id " +
            "WHERE application.ces_id = ? AND interviewee.special_mark = ? OR interviewee.special_mark = ?;";

    private static final String GET_REJECTED_INTERVIEWEE = "SELECT * FROM system_user " +
            "JOIN application ON system_user.system_user_id = application.system_user_id " +
            "JOIN interviewee ON application.application_id = interviewee.application_id " +
            "WHERE application.ces_id = ? AND interviewee.special_mark = ? OR (interviewee.special_mark = ?) IS NOT FALSE";

    // The next methods are used for interviewee mailing.

    private static final String JOB_OFFER = "job offer";
    private static final String TAKE_ON_COURSES = "take on courses";
    private static final String REJECTED = "rejected";
    private static final String GET_ON_COURSES = "get on courses";

    @Override
    public Set<User> getJobOfferedUsers(Integer cesId) throws DAOException {
        return getSomeInterviewee(GET_SOME_INTERVIEWEE, cesId, JOB_OFFER, JOB_OFFER);
    }

    @Override
    public Set<User> getCourseAcceptedUsers(Integer cesId) throws DAOException {
        return getSomeInterviewee(GET_SOME_INTERVIEWEE, cesId, TAKE_ON_COURSES, GET_ON_COURSES);
    }

    @Override
    public Set<User> getCourseRejectedStudents(Integer cesId) throws DAOException {
        return getSomeInterviewee(GET_REJECTED_INTERVIEWEE, cesId, REJECTED, "");
    }

    private Set<User> getSomeInterviewee(String query, Integer cesId, String specialMark1, String specialMark2) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cesId);
            statement.setString(2, specialMark1);
            statement.setString(3, specialMark2);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            } else {
                return getStudentsParse(rs);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private static final String GET_HRBA_COUNT = "SELECT COUNT(*) FROM system_user " +
            "JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id " +
            "JOIN role ON role.role_id = system_user_role.role_id " +
            "JOIN interviewer_participation ON system_user.system_user_id = interviewer_participation.system_user_id " +
            "WHERE interviewer_participation.ces_id = ? AND (role.role_id = 3 OR role.role_id = 5)";

    private static final String GET_DEV_COUNT = "SELECT COUNT(*) FROM system_user " +
            "JOIN system_user_role ON system_user.system_user_id = system_user_role.system_user_id " +
            "JOIN role ON role.role_id = system_user_role.role_id " +
            "JOIN interviewer_participation ON system_user.system_user_id = interviewer_participation.system_user_id " +
            "WHERE interviewer_participation.ces_id = ? AND role.role_id = 4";

    @Override
    public Integer getDEVCount(Integer cesId) throws DAOException {
        return getRoleCount(GET_DEV_COUNT, cesId);
    }

    @Override
    public Integer getHRBACount(Integer cesId) throws DAOException {
        return getRoleCount(GET_HRBA_COUNT, cesId);
    }

    private Integer getRoleCount(String query, Integer cesId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, cesId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("count");
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private static final String IS_APPLIED_AS_INTERVIEWER = "SELECT * FROM interviewer_participation WHERE system_user_id = ? AND ces_id = ?";

    @Override
    public Boolean isAppliedAsInterviewer(Integer userId, Integer cesId) throws DAOException{
        try (PreparedStatement statement = connection.prepareStatement(IS_APPLIED_AS_INTERVIEWER)) {
            statement.setInt(1, userId);
            statement.setInt(2, cesId);
            ResultSet rs = statement.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /////////////////////////////

    private class PersistUser extends User {
        public PersistUser(Integer id, String name, String surname, String email, String password, Set<Role> roles) {
            super(id, name, surname, email, password, roles);
        }

        public PersistUser() {
        }

        @Override
        public void setId(Integer id) {
            super.setId(id);
        }
    }


}
