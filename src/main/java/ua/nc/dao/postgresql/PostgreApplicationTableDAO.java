package ua.nc.dao.postgresql;

import ua.nc.dao.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private static final String getFieldIdsQuery = (
            "SELECT field.field_id FROM field " +
                    "JOIN ces_field ON field.field_id = ces_field.field_id " +
                    "JOIN field_type ON field.field_type_id = field_type.field_type_id " +
                    "WHERE ces_id = ? " +
                    "AND field.multiple_choice = FALSE " +
                    "AND field_type.name != 'tel' " +
                    "AND field_type.name != 'textarea'"
    );

    public List<Integer> getFieldIds() throws DAOException {
        try (PreparedStatement statement = this.connection.prepareStatement(getFieldIdsQuery)) {
            statement.setInt(1, 1);
            ResultSet rs = statement.executeQuery();
            List<Integer> fieldIds = new LinkedList<>();
            while (rs.next()){
                fieldIds.add(rs.getInt("field_id"));
            }
            return fieldIds;
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

    public void getApplications() throws DAOException {
        List<Integer> fieldIds = getFieldIds();
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
                "WHERE field.multiple_choice = FALSE " +
                "GROUP BY system_user.system_user_id"
        );
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fieldIds.size() - 1; i++) {
            builder.append(subQuery(fieldIds.get(i)));
            builder.append(",");
        }
        builder.append(subQuery(fieldIds.get(fieldIds.size() - 1)));
        String sub = builder.toString();
        String fullQuery = MessageFormat.format(baseQuery, sub);
        try (PreparedStatement statement = this.connection.prepareStatement(fullQuery)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("system_user_id"));
                System.out.println(rs.getString("name"));
                for (int i = 0; i < fieldIds.size(); i++) {
                    String fieldName = MessageFormat.format("field_{0}", i);
                    Object fieldValue = rs.getObject(fieldName);
                    System.out.println(fieldValue);
                }
            }
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
