package ua.nc.dao.postgresql;

import ua.nc.dao.AppSetting;
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
 * Created by Pavel on 22.04.2016.
 */
public class PostgreRoleDAO implements RoleDAO {
    /*    private static final Logger LOGGER = Logger.getLogger(PostgreRoleDAO.class);*/
    private final Connection connection;

    public PostgreRoleDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Role findByName(String name) throws DAOException {
        String sql = AppSetting.get("role.findByName");
        Role role = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            resultSet.next();
            role = new Role();
            role.setId(resultSet.getInt("role_id"));
            role.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            System.out.println("Role" + name + "  not found");
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
        return role;
    }

    @Override
    public Set<Role> findByEmail(String email) throws DAOException {
        String sql = AppSetting.get("role.findByEmail");
        Set<Role> roles = new HashSet<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("role_id"));
                role.setName(resultSet.getString("name"));
                System.out.println(role.getName());
                roles.add(role);
            }
        } catch (SQLException e) {
            System.out.println("Roles for " + email + "  not found");
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
        return roles;
    }

    @Override
    public void setRoleToUser(Set<Role> roles, User user) throws DAOException {
        String sql = "INSERT INTO public.user_role(role_id, user_id) SELECT ?, user_id FROM public.user u WHERE u.email=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql);
            for (Role role : roles) {
                statement.setInt(1, role.getId());
                statement.setString(2, user.getEmail());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Cant set roles to user" + user.getName());
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
}
