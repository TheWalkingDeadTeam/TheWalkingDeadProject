package ua.nc.dao.postgresql;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.profile.FieldData;
import ua.nc.entity.profile.RowValue;
import ua.nc.entity.profile.StudentData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rangar on 08.05.2016.
 */
public class PostgreApplicationTableDAO {
    private Connection connection;

    public PostgreApplicationTableDAO(Connection connection) {
        this.connection = connection;
    }

    private static final String GET_FIELD_IDS_QUERY = (
            "SELECT field.field_id, field.name, field_type.name AS type FROM field " +
                    "JOIN ces_field ON field.field_id = ces_field.field_id " +
                    "JOIN field_type ON field.field_type_id = field_type.field_type_id " +
                    "WHERE ces_id = ? " +
                    "AND field.multiple_choice = FALSE " +
                    "AND field_type.name != 'tel' " +
                    "AND field_type.name != 'textarea'"
    );

    private static final String BASE_QUERY = (
            "SELECT " +
                    "system_user.system_user_id, " +
                    "application.rejected, " +
                    "system_user.name, " +
                    "system_user.surname as field_0, {0} " +
                    "FROM public.application " +
                    "JOIN public.system_user ON application.system_user_id = system_user.system_user_id " +
                    "JOIN public.course_enrollment_session ON course_enrollment_session.ces_id = application.ces_id " +
                    "JOIN public.ces_field ON ces_field.ces_id = course_enrollment_session.ces_id " +
                    "JOIN public.field ON field.field_id = ces_field.field_id " +
                    "JOIN public.field_type ON field.field_type_id = field_type.field_type_id " +
                    "JOIN public.field_value ON field_value.field_id = field.field_id " +
                    "LEFT JOIN public.list_value ON field_value.list_value_id = list_value.list_value_id " +
                    "WHERE course_enrollment_session.ces_id = ? AND system_user.surname LIKE ? OR system_user.name LIKE ? " +
                    "GROUP BY system_user.system_user_id, application.rejected " +
                    "ORDER BY field_{1} {2} " +
                    "LIMIT ? OFFSET ?"
    );

    public List<FieldData> getFieldIds(Integer cesId) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(GET_FIELD_IDS_QUERY)) {
            statement.setInt(1, cesId);
            ResultSet rs = statement.executeQuery();
            List<FieldData> fieldData = new LinkedList<>();
            while (rs.next()){
                FieldData field = new FieldData();
                field.id = rs.getInt("field_id");
                field.name = rs.getString("name");
                field.type = rs.getString("type");
                fieldData.add(field);
            }
            return fieldData;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private String subQuery(Integer fieldId, String fieldType) {
        String pattern = null;
        switch (fieldType){
            case "number":
                pattern = "field_value.value_double";
                break;
            case "text":
                pattern = "field_value.value_text";
                break;
            case "date":
                pattern = "field_value.value_date";
                break;
            case "select":
            case "radio":
            case "checkbox":
                pattern = "list_value.value_text";
                break;
        }
        String template = (
            "MAX(CASE WHEN field.field_id={0} THEN {1} ELSE NULL END) as field_{0} "
        );
        return MessageFormat.format(template, fieldId, pattern);
    }


    public StudentData getApplicationsTable(Integer cesId) throws DAOException {
        return getApplications(cesId, null, null, 0, "", true);
    }

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset) throws DAOException {
        return getApplications(cesId, limit, offset, 0, "", true);
    }

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy) throws DAOException {
        return getApplications(cesId, limit, offset, orderBy, "", true);
    }

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy, Boolean asc) throws DAOException {
        return getApplications(cesId, limit, offset, orderBy, "", asc);
    }

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, String pattern) throws DAOException {
        return getApplications(cesId, limit, offset, 0, pattern, true);
    }

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy, String pattern, Boolean asc) throws DAOException {
        return getApplications(cesId, limit, offset, orderBy, pattern, asc);
    }

    private StudentData getApplications(Integer cesId, Integer limit, Integer offset, Integer orderBy, String pattern, Boolean asc) throws DAOException {
        List<FieldData> fieldData = getFieldIds(cesId);
        String baseQuery = buildBaseFullQuery(fieldData, orderBy, asc);
        try (PreparedStatement statement = this.connection.prepareStatement(baseQuery)) {
            statement.setInt(1, cesId);
            statement.setString(2, "%" + pattern + "%");
            statement.setString(3, "%" + pattern + "%");
            statement.setObject(4, limit);
            statement.setObject(5, offset);
            ResultSet rs = statement.executeQuery();
            return parseResultSet(rs, fieldData);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private String buildBaseFullQuery(List<FieldData> fieldData, Integer fieldId, Boolean asc) throws DAOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fieldData.size() - 1; i++) {
            builder.append(subQuery(fieldData.get(i).id, fieldData.get(i).type));
            builder.append(",");
        }
        builder.append(subQuery(fieldData.get(fieldData.size() - 1).id, fieldData.get(fieldData.size() - 1).type));
        String sub = builder.toString();
        String order = asc ? "ASC" : "DESC";
        return MessageFormat.format(BASE_QUERY, sub, fieldId, order);
    }

    private StudentData parseResultSet(ResultSet rs, List<FieldData> fieldData) throws SQLException {
        StudentData result = new StudentData();
        result.header = fieldData;
        List<RowValue> rowValues = new LinkedList<>();
        while (rs.next()){
            RowValue rowValue = new RowValue();
            rowValue.userId = rs.getInt("system_user_id");
            rowValue.rejected = rs.getBoolean("rejected");
            rowValue.name = rs.getString("field_0")+ " " + rs.getString("name");
            for (FieldData i : fieldData) {
                rowValue.fields.put(i.id, getField(rs, i));
            }
            rowValues.add(rowValue);
        }
        result.rows = rowValues;
        return result;
    }

    private Object getField(ResultSet rs, FieldData fd) throws SQLException {
        Object result = null;
        String fieldName = MessageFormat.format("field_{0}", fd.id);
        switch (fd.type){
            case "number":
                result = rs.getDouble(fieldName);
                break;
            case "text":
                result = rs.getString(fieldName);
                break;
            case "date":
                result = rs.getDate(fieldName);
                break;
            case "select":
            case "radio":
            case "checkbox":
                result = rs.getString(fieldName);
                break;
        }
        return result;
    }
}
