package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.RoleDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Role;
import ua.nc.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Pavel on 22.04.2016.
 */
public class PostgreRoleDAO extends AbstractPostgreDAO<Role, Integer> implements RoleDAO {
    private static final Logger LOGGER = Logger.getLogger(PostgreRoleDAO.class);

    private static final String SELECT = "SELECT * FROM public.role r WHERE r.role_id = ?";
    private static final String UPDATE = "UPDATE public.role r SET (r.name = ?, r.description = ?) WHERE r.role_id = ?";
    private static final String INSERT = "INSERT INTO public.role r (r.name, r.description) VALUES (?,?)";
    private static final String GET_ALL = "SELECT * FROM public.role";
    private static final String FIND_BY_NAME = "SELECT * FROM public.role r WHERE r.name = ?";
    private static final String FIND_BY_EMAIL = "SELECT  r.role_id, r.name FROM public.system_user u " +
            "JOIN public.system_user_role ur on u.system_user_id = ur.system_user_id " +
            "JOIN public.role r ON ur.role_id = r.role_id WHERE u.email = ?";
    //////////////////////// vdanchul
    private static String ADD_ROLES = "INSERT INTO system_user_role(role_id, system_user_id) VALUES (?, ?);";
    private static String REMOVE_ROLES = "DELETE FROM system_user_role WHERE system_user_id = ?;";
    private static String GET_ROLES_COUNT = "SELECT COUNT(1) FROM system_user_role WHERE system_user_id = ?";

    public PostgreRoleDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Role findByName(String name) throws DAOException {
        Role role = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(FIND_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            resultSet.next();
            role = new Role();
            role.setId(resultSet.getInt("role_id"));
            role.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            LOGGER.info("Role" + name + "  not found");
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
        Set<Role> roles = new HashSet<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(FIND_BY_EMAIL);
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("role_id"));
                role.setName(resultSet.getString("name"));
                roles.add(role);
            }
        } catch (SQLException e) {
            LOGGER.info("Roles for " + email + "  not found" + e.getMessage());
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
    public void setRolesToUser(Set<Role> roles, User user) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(ADD_ROLES)) {
            for (Role role : roles) {
                statement.setInt(1, role.getId());
                statement.setInt(2, user.getId());
                statement.addBatch();
            }
            int[] count = statement.executeBatch();
            for (int i : count) {
                if (i != 1) throw new DAOException("Affected more than one row: " + count);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Integer getRolesCount(Integer userId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(GET_ROLES_COUNT)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("count");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public void removeRolesFromUser(User user) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_ROLES)) {
            statement.setInt(1, user.getId());
            int needed = getRolesCount(user.getId());
            int count = statement.executeUpdate();
            if (count != needed) {
                throw new DAOException("Only " + count + " rows were affected. Needed: " + needed);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    //////////////////////////////////////

    @Override
    public String getSelectQuery() {
        return SELECT;
    }

    @Override
    public String getCreateQuery() {
        return INSERT;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE;
    }

    @Override
    public String getAllQuery() {
        return GET_ALL;
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws DAOException {
        List<Role> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistRole role = new PersistRole();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                result.add(role);
            }
        } catch (SQLException e) {

        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Role object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
        } catch (SQLException e) {

        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getId());
        } catch (SQLException e) {

        }
    }


    @Override
    public Role create(Role object) throws DAOException {
        return null;
    }

    private class PersistRole extends Role {

        @Override
        public void setId(Integer id) {
            super.setId(id);
        }
    }
}
