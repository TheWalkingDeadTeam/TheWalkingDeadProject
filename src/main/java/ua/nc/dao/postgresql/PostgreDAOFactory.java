package ua.nc.dao.postgresql;

import ua.nc.dao.RoleDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.pool.ConnectionPool;

/**
 * Created by Pavel on 21.04.2016.
 */
public class PostgreDAOFactory extends DAOFactory {

    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool(DataBaseType.POSTGRESQL);

//    @Override
//    public UserDAO getUserDAO(Role role) {
//        switch (role) {
//            case ROLE_STUDENT:
//                return new PostgreStudentDAO(connectionPool);
//            case ROLE_BA:
//                return null;
//            case ROLE_DEV:
//                return null;
//            case ROLE_HR:
//                return null;
//            case ROLE_ADMIN:
//                return null;
//            default:
//                return new PostgreStudentDAO(connectionPool);
//        }
//
//    }
    @Override
    public UserDAO getUserDAO(){
        return new PostgreStudentDAO(connectionPool);
    }

    @Override
    public RoleDAO getRoleDAO() {
        return new PostgreRoleDAO(connectionPool);
    }


}
