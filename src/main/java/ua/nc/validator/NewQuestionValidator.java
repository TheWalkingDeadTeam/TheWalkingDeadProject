package ua.nc.validator;

import ua.nc.dao.FieldDAO;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.entity.profile.Field;
import ua.nc.service.EditFormService;
import ua.nc.service.EditFormServiceImpl;

import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neltarion on 13.05.2016.
 */
public class NewQuestionValidator implements Validator {

    private static String NAME_PATTERN = "[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public Set<ValidationError> validate(Object obj) {
        EditFormService efs = new EditFormServiceImpl();
        List<Field> fields = efs.getAllFields(1);
        Integer questions = fields.size();

        Field field = (Field) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();
        if (field.getName() != null && !field.getName().isEmpty()) {
            pattern = Pattern.compile(NAME_PATTERN);
            matcher = pattern.matcher(field.getName());
            if (!matcher.matches()) {
                errors.add(new ValidationError("field name", "Enter correct field name"));
            }
            if (field.getName().length() > 120) {
                errors.add(new ValidationError("field name", "Field name length should be less than 120"));
            }
        } else {
            errors.add(new ValidationError("field name", "Field name shouldn't be empty"));
        }

        if (field.getFieldTypeID() <= 0 || field.getFieldTypeID() > 8) {//TODO replace digit with proper method
            errors.add(new ValidationError("field type", "Choose correct type"));
        } else {
            if (!(((Integer)field.getFieldTypeID()) instanceof Integer)) {
                errors.add(new ValidationError("field type", "Choose correct type, Mr.Hacker"));
            }
        }

        if (!(((Boolean)field.getMultipleChoice()) instanceof Boolean)) {
            errors.add(new ValidationError("multiple field choice", "Choose correct multiple type"));
        }

        if (field.getOrderNum() > 0 && field.getOrderNum() <= questions) {
            if (!(((Integer)field.getOrderNum()) instanceof Integer)) {
                errors.add(new ValidationError("order number", "Choose correct order number, Mr.Hacker"));
            }
        }
//        else {
//            errors.add(new ValidationError("order number", "Choose in range [1-"+questions+"]"));
//        }

        if (field.getListTypeID() != null) {
            // TODO: 13.05.2016  do something
        }

        return errors;
    }
}
