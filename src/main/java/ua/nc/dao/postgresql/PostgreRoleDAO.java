package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.pool.ConnectionPool;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Neltarion on 23.04.2016.
 */
public class PostgreRoleDAO extends RoleDAO {

    private static final Logger LOGGER = Logger.getLogger(PostgreRoleDAO.class);
    private static final String sqlFindByName = "SELECT * FROM public.role r WHERE r.name = ?";
    private static final String sqlFindByEmail = "SELECT  r.role_id, r.name FROM public.user u JOIN public.user_role ur on u.user_id = ur.user_id JOIN public.role r ON ur.role_id = r.role_id WHERE u.email = ?";
    private static final String sqlSetRoleToUser = "INSERT INTO public.user_role(role_id, user_id)  SELECT ?, user_id FROM public.user u WHERE u.email=?";
    private final ConnectionPool connectionPool;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public PostgreRoleDAO(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Role findByName(String name) throws DAOException {
        Role role = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlFindByName);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            role = new Role();
            role.setId(resultSet.getInt("role_id"));
            role.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
        return role;
    }

    @Override
    public Set<Role> findByEmail(String email) throws DAOException {
        Set<Role> roles = new HashSet<Role>();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sqlFindByEmail);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("role_id"));
                role.setName(resultSet.getString("name"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
        return roles;
    }

    @Override
    public void setRoleToUser(Set<Role> roles, User user) throws DAOException {
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            roles.forEach(r -> {
                try {
                    preparedStatement = connection.prepareStatement(sqlSetRoleToUser);
                    preparedStatement.setInt(1, r.getId());
                    preparedStatement.setString(2, user.getEmail());
                    preparedStatement.addBatch();
                } catch (SQLException e) {
                    LOGGER.error(e);
                }
            });
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnStmt(connectionPool, connection, preparedStatement, resultSet);
        }
    }
}
