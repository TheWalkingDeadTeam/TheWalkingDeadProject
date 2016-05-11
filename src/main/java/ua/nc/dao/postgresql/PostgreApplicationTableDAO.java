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
import java.util.ArrayList;
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

    private static final String getFieldIdsQuery = (
            "SELECT field.field_id, field.name, field_type.name AS type FROM field " +
                    "JOIN ces_field ON field.field_id = ces_field.field_id " +
                    "JOIN field_type ON field.field_type_id = field_type.field_type_id " +
                    "WHERE ces_id = ? " +
                    "AND field.multiple_choice = FALSE " +
                    "AND field_type.name != 'tel' " +
                    "AND field_type.name != 'textarea'"
    );

    public List<FieldData> getFieldIds(Integer cesId) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(getFieldIdsQuery)) {
            statement.setInt(1, cesId);
            ResultSet rs = statement.executeQuery();
            List<FieldData> fieldData = new ArrayList<>();
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

    private static final String baseQuery = (
            "SELECT " +
                    "system_user.system_user_id, " +
                    "system_user.name, " +
                    "system_user.surname, {0} " +
                    "FROM public.application " +
                    "JOIN public.system_user ON application.system_user_id = system_user.system_user_id " +
                    "JOIN public.course_enrollment_session ON course_enrollment_session.ces_id = application.ces_id " +
                    "JOIN public.ces_field ON ces_field.ces_id = course_enrollment_session.ces_id " +
                    "JOIN public.field ON field.field_id = ces_field.field_id " +
                    "JOIN public.field_type ON field.field_type_id = field_type.field_type_id " +
                    "JOIN public.field_value ON field_value.field_id = field.field_id " +
                    "LEFT JOIN public.list_value ON field_value.list_value_id = list_value.list_value_id " +
                    "WHERE course_enrollment_session.ces_id = ? " +
                    "GROUP BY system_user.system_user_id "
    );
// TODO 05/11/16 naming convention
    private static final String orderByQuery = " ORDER BY field_{0} ";
    private static final String limitOffsetQuery = " LIMIT {0} OFFSET {1} ";
    private static final String matchingQuery = "";
    private static final String orderByBaseFieldQuery = " ORDER BY {0} ";

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset) throws DAOException {
        List<FieldData> fieldData = getFieldIds(cesId);
        String baseQuery = buildBaseFullQuery(fieldData);
        String fullQuery = baseQuery + MessageFormat.format(limitOffsetQuery, limit, offset);
        return getApplications(cesId, fullQuery, fieldData);
    }

    public StudentData getApplicationsTable(Integer cesId, Integer limit, Integer offset, Integer orderBy) throws DAOException {
        List<FieldData> fieldData = getFieldIds(cesId);
        String baseQuery = buildBaseFullQuery(fieldData);
        String almostFullQuery = baseQuery + MessageFormat.format(orderByQuery, orderBy);
        String fullQuery = almostFullQuery + MessageFormat.format(limitOffsetQuery, limit, offset);
        return getApplications(cesId, fullQuery, fieldData);
    }

    public StudentData getApplicationsTable(Integer cesId) throws DAOException {
        List<FieldData> fieldData = getFieldIds(cesId);
        return getApplications(cesId, buildBaseFullQuery(fieldData), fieldData);
    }

    private StudentData getApplications(Integer cesId, String fullQuery, List<FieldData> fieldData) throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(fullQuery)) {
            statement.setInt(1, cesId);
            ResultSet rs = statement.executeQuery();
            return parseResultSet(rs, fieldData);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private String buildBaseFullQuery(List<FieldData> fieldData) throws DAOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fieldData.size() - 1; i++) {
            builder.append(subQuery(fieldData.get(i).id, fieldData.get(i).type));
            builder.append(",");
        }
        builder.append(subQuery(fieldData.get(fieldData.size() - 1).id, fieldData.get(fieldData.size() - 1).type));
        String sub = builder.toString();
        return MessageFormat.format(baseQuery, sub);
    }

    private StudentData parseResultSet(ResultSet rs, List<FieldData> fieldData) throws SQLException {
        StudentData result = new StudentData();
        result.header = fieldData;
        List<RowValue> rowValues = new LinkedList<>();
        while (rs.next()){
            RowValue rowValue = new RowValue();
            rowValue.userId = rs.getInt("system_user_id");
            rowValue.name = rs.getString("name") + " " + rs.getString("surname");
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

    public void tempMethod(Integer cesId, Integer limit, Integer offset, Object orderBy, String pattern) throws DAOException {
        if (cesId != null){
            List<FieldData> fieldData = getFieldIds(cesId);
            String query = buildBaseFullQuery(fieldData);
            if (limit != null && offset != null){
                if (orderBy != null){
                    if (isInteger(orderBy.toString())){
                        query = query + MessageFormat.format(orderByQuery, orderBy);
                    } else {
                        query = query + MessageFormat.format(orderByBaseFieldQuery, orderBy);
                    }
                }
                query = query + MessageFormat.format(limitOffsetQuery, limit, offset);
            }
            System.out.println();
        }
    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
