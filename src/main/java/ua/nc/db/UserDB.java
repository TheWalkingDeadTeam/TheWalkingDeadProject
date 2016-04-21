package ua.nc.db;

import ua.nc.entity.User;
import ua.nc.entity.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pavel on 18.04.2016.
 */
public class UserDB {
    private static PostgresConnectionPool connectionPool = new PostgresConnectionPool();
    private final String sqlFindByEmail= "SELECT * FROM public.user u WHERE u.email = ?";
    private final String sqlFindRoleByEmail= "SELECT r.name FROM public.user u " +
            "JOIN public.user_role ur on u.user_id = ur.user_id " +
            "JOIN public.role r ON ur.role_id = r.role_id " +
            "WHERE u.email = ?";
    private final String sqlCreateUser = "INSERT INTO public.user(name, email, password) " +
            "VALUES (?, ?, ?)";
    private final String sqlCreateRoleToUser = "INSERT INTO public.user_role(user_id, role_id) " +
            "VALUES (?, ?)";


    public User findByEmail(String email) throws SQLException {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sqlFindByEmail);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            System.out.println(user.getName());
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                throw e;//toDo log4j
            }
        }
        return user;
    }

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
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                throw e;//toDo log4j
            }
        }
        return roles;
    }

    public void createUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sqlCreateUser);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                throw e;//toDo log4j
            }
        }
    }

    public void createRoleToUser(User user, Role role) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(sqlCreateRoleToUser);
            statement.setInt(1, user.getId());
            statement.setInt(2, role.ordinal() + 1);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
                throw e;//toDo log4j
            }
        }
    }


}
