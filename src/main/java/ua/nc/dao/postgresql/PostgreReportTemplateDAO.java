package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ReportTemplateDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.ReportTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreReportTemplateDAO extends AbstractPostgreDAO<ReportTemplate, Integer> implements ReportTemplateDAO {
    private final static Logger LOGGER = Logger.getLogger(PostgreReportTemplateDAO.class);

    public PostgreReportTemplateDAO(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM report_template WHERE report_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO report_template (query, name) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE report_template SET query = ?, name = ? WHERE report_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM report_template";
    }

    public String getDeleteQuery() {
        return "DELETE FROM report_template WHERE report_id = ?;";
    }

    @Override
    protected List<ReportTemplate> parseResultSet(ResultSet rs) throws DAOException {
        List<ReportTemplate> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistReportTemplate reportTemplate = new PersistReportTemplate(rs.getString("query"), rs.getString("name"));
                reportTemplate.setId(rs.getInt("report_id"));
                result.add(reportTemplate);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, ReportTemplate object) throws DAOException {
        try {
            statement.setString(1, object.getQuery());
            statement.setString(2, object.getName());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, ReportTemplate object) throws DAOException {
        try {
            statement.setString(1, object.getQuery());
            statement.setString(2, object.getName());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    protected void prepareStatementForDelete(PreparedStatement statement, ReportTemplate object) throws DAOException {
        try {
            statement.setInt(1, object.getId());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public ReportTemplate create(ReportTemplate object) throws DAOException {
        return persist(object);
    }


    @Override
    public void delete(ReportTemplate report) throws DAOException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForDelete(statement, report);
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new DAOException("Delete  more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private class PersistReportTemplate extends ReportTemplate {
        public PersistReportTemplate(String query, String name) {
            super(query, name);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }

    public List<Map<String, Object>> execute(ReportTemplate report) throws DAOException {
        String sql = report.getQuery();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> rows = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> columns = new LinkedHashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                rows.add(columns);
            }
            if (rows.isEmpty()) {
                Map<String, Object> columns = new LinkedHashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    columns.put(metaData.getColumnLabel(i), null);
                }
                rows.add(columns);
            }
            LOGGER.debug("Execute query " + report.getName());
        } catch (SQLException e) {
            LOGGER.info("Cant execute query " + report.getName(), e.getCause());
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
        return rows;
    }
}
