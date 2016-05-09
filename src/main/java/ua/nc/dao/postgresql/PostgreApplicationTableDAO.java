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
            "SELECT field.field_id, field.name FROM field " +
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
                fieldData.add(field);
            }
            return fieldData;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private String subQuery(Integer fieldId) {
        String template = (
            "MAX(CASE WHEN field.field_id={0} THEN list_value.value_text ELSE NULL END) as field_{0}_list_text," +
            "MAX(CASE WHEN field.field_id={0} THEN field_value.value_text ELSE NULL END) as field_{0}_text," +
            "MAX(CASE WHEN field.field_id={0} THEN field_value.value_date ELSE NULL END) as field_{0}_date," +
            "MAX(CASE WHEN field.field_id={0} THEN field_value.value_double ELSE NULL END) as field_{0}_double"
        );
        return MessageFormat.format(template, fieldId);
    }

    public StudentData getApplications(Integer cesId) throws DAOException {
        List<FieldData> fieldData = getFieldIds(cesId);
        String baseQuery = (
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
                "GROUP BY system_user.system_user_id"
        );
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fieldData.size() - 1; i++) {
            builder.append(subQuery(fieldData.get(i).id));
            builder.append(",");
        }
        builder.append(subQuery(fieldData.get(fieldData.size() - 1).id));
        String sub = builder.toString();
        String fullQuery = MessageFormat.format(baseQuery, sub);
        StudentData result = new StudentData();
        result.header = fieldData;
        List<RowValue> rowValues = new LinkedList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(fullQuery)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                RowValue rowValue = new RowValue();
                rowValue.userId = rs.getInt("system_user_id");
                rowValue.name = rs.getString("name") + " " + rs.getString("surname");
                for (FieldData i : fieldData) {
                    rowValue.fields.put(i.id, parseResult(rs, i.id));
                }
                rowValues.add(rowValue);
            }
            result.rows = rowValues;
            return result;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    private Object parseResult(ResultSet rs, int i) throws SQLException {
        Object result;
        String fieldName = MessageFormat.format("field_{0}_list_text", i);
        if (rs.getObject(fieldName) != null){
            result = rs.getString(fieldName);
        } else {
            fieldName = MessageFormat.format("field_{0}_text", i);
            if (rs.getObject(fieldName) != null){
                result = rs.getString(fieldName);
            } else {
                fieldName = MessageFormat.format("field_{0}_double", i);
                if (rs.getObject(fieldName) != null){
                    result = rs.getDouble(fieldName);
                } else {
                    fieldName = MessageFormat.format("field_{0}_date", i);
                    result = rs.getDate(fieldName);
                }
            }
        }
        return result;
    }
}
