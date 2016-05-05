package ua.nc.dao.postgresql;

import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.ReportTemplateDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.ReportTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rangar on 02.05.2016.
 */
public class PostgreReportTemplateDAO extends AbstractPostgreDAO<ReportTemplate, Integer> implements ReportTemplateDAO {
    public PostgreReportTemplateDAO(Connection connection){
        super(connection);
    }

    private class PersistReportTemplate extends ReportTemplate{
        public PersistReportTemplate(String query, String name) {
            super(query, name);
        }

        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM report_template WHERE report_template_id = ?";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO report_template (query, name) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE report_template SET query = ?, name = ? WHERE report_template_id = ?;";
    }

    @Override
    public String getAllQuery() {
        return "SELECT * FROM report_template";
    }

    @Override
    protected List<ReportTemplate> parseResultSet(ResultSet rs) throws DAOException {
        List<ReportTemplate> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PersistReportTemplate reportTemplate = new PersistReportTemplate(rs.getString("query"), rs.getString("name"));
                reportTemplate.setId(rs.getInt("report_template_id"));
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

    @Override
    protected void prepareStatementForSelect(PreparedStatement statement, ReportTemplate object) throws DAOException {
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
}
