package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nc.dao.UserDAO;
import ua.nc.dao.connectionPool.ConnectionPool;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.User;
import ua.nc.entity.enums.Role;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class PostgreStudentDAO extends UserDAO {

    private static final Logger LOGGER = Logger.getLogger(PostgreStudentDAO.class);
    private final String sqlFindByEmail = "SELECT * FROM public.user u WHERE u.email = ?";
    private final String sqlFindRoleByEmail = "SELECT r.name FROM public.user u " +
            "JOIN public.user_role ur on u.user_id = ur.user_id " +
            "JOIN public.role r ON ur.role_id = r.role_id " +
            "WHERE u.email = ?";
    private final String sqlCreateUser = "INSERT INTO public.user(name, email, password) " +
            "VALUES (?, ?, ?)";
    private final String sqlCreateRoleToUser = "INSERT INTO public.user_role(user_id, role_id) " +
            "VALUES (?, ?)";
    ConnectionPool connectionPool;
    Connection connection;
    PreparedStatement preparedStatement;

    public PostgreStudentDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<User> findUsersByName(String name) throws DAOException {
        return null;
    }

    @Override
    public User findUserByEmail(String email) throws DAOException {
        User user = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlFindByEmail);
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setId(rs.getInt("user_id"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }

        return user;
    }

    @Override
    public Set<Role> findRoleByEmail(String email) throws SQLException {
        Set<Role> roles = new HashSet<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sqlFindRoleByEmail);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String role = resultSet.getString("name");
                System.out.println(role);
                roles.add(Role.valueOf(role));
            }
        } catch (SQLException e) {
            throw e;
        } catch (DAOException e) {
            LOGGER.error(e);
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                LOGGER.error(e);
                throw e;
            }
        }
        return roles;
    }

    @Override
    public User get(int id) throws DAOException {
        return null;
    }

    @Override
    public int create(User user) throws DAOException {
        int id = 0;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlCreateUser, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            id = rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                LOGGER.error(e);
            }
        }
        return id;
    }

    public void createRoleToUser(int id, Role role) throws SQLException {
        try {

            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlCreateRoleToUser);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, role.ordinal() + 1);
            preparedStatement.executeUpdate();
        } catch (DAOException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        }
    }

    @Override
    public void update(User user) throws DAOException {

    }

    @Override
    public void delete(int id) throws DAOException {

    }
}
