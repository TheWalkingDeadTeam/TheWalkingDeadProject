package ua.nc.pool;

import ua.nc.dao.exception.DataSourceException;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NoInitialContextException;
import javax.sql.DataSource;
import java.sql.Connection;


/**
 * Created by Pavel on 21.04.2016.
 */
public class ConnectionPool {

    private ConnectionPool() {
    }

    public static Connection getConnection() throws Exception {
        Context cxt = new InitialContext();
        if ( cxt == null ) {
            throw new NoInitialContextException("No context was defined!");
        }
        DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/WD" );
        if ( ds == null ) {
            throw new DataSourceException("No data source was defined");
        }
        return ds.getConnection();
    }
}
