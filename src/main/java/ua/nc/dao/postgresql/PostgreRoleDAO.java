package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Pavel on 22.04.2016.
 */
public class PostgreRoleDAO extends AbstractPostgreDAO<Role,Integer> implements RoleDAO {
    /*    private static final Logger LOGGER = Logger.getLogger(PostgreRoleDAO.class);*/

    private static final String SELECT = "SELECT * FROM public.role r WHERE r.role_id = ?";
    private static final String UPDATE = "UPDATE public.role r SET (r.name = ?, r.description = ?) WHERE r.role_id = ?";
    private static final String INSERT = "INSERT INTO public.role r (r.name, r.description) VALUES (?,?)";
    private static final String GET_ALL = "SELECT * FROM public.role";
    private static final String FIND_BY_NAME = "SELECT * FROM public.role r WHERE r.name = ?";

    private class PersistRole extends Role{

        @Override
        public void setId(Integer id) {
            super.setId(id);
        }
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
        try{
            while(rs.next()){
                PersistRole role = new PersistRole();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("description"));
                result.add(role);
            }
        }catch(SQLException e) {

        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Role object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
        } catch (SQLException e){

        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws DAOException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3,object.getId());
        } catch (SQLException e){

        }
    }

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, Role object) throws DAOException {
        try{
            statement.setInt(1,object.getId());
        } catch (SQLException e){

        }
    }

    @Override
    public Role create(Role object) throws DAOException {
        return null;
    }

    public PostgreRoleDAO(Connection connection) {
        super(connection);
    }
}
