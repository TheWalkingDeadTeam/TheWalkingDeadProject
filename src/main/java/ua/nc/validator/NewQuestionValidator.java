package ua.nc.validator;

import ua.nc.entity.FullFieldWrapper;
import ua.nc.entity.OptionWrapper;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neltarion on 13.05.2016.
 */
public class NewQuestionValidator implements Validator {

    private static String NAME_PATTERN = "[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)";
    private static String FIELD_PATTERN = "^[a-zA-Zа-яА-Я0-9_.-]*$";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public Set<ValidationError> validate(Object obj) {

        FullFieldWrapper fullFieldWrapper = (FullFieldWrapper) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();
        fullFieldWrapper.getInputOptionsFields();
        if (fullFieldWrapper != null) {
            if (fullFieldWrapper.getName() != null && !fullFieldWrapper.getName().isEmpty()) {
                pattern = Pattern.compile(NAME_PATTERN);
                matcher = pattern.matcher(fullFieldWrapper.getName());
                if (!matcher.matches()) {
                    errors.add(new ValidationError("field name", "Field name should contain only letters, numbers and '_' "));
                } else {
                    if (fullFieldWrapper.getName().length() < 5 || fullFieldWrapper.getName().length() > 100) {
                        errors.add(new ValidationError("field name", "Field name should have not more than 100 but not less than 5 characters"));
                    }
                }
            }

            if (fullFieldWrapper.getFieldTypeID() <= 0 || fullFieldWrapper.getFieldTypeID() > 8) {//TODO replace digit with proper method
                errors.add(new ValidationError("field type", "Choose correct type"));
            } else {
                if (!(((Integer) fullFieldWrapper.getFieldTypeID()) instanceof Integer)) {
                    errors.add(new ValidationError("field type", "Choose correct type, Mr.Hacker"));
                }
            }

            if (!fullFieldWrapper.getListTypeName().isEmpty() || !fullFieldWrapper.getListTypeName().equals("") || !fullFieldWrapper.getListTypeName().trim().equals("")) {
                if (fullFieldWrapper.getInputOptionsFields() == null || fullFieldWrapper.getInputOptionsFields().isEmpty()) {
                    errors.add(new ValidationError("new options", "If select, radio or checkbox is chosen, both - \"Option group name\" field and option fields must be filled"));
                } else {
                    int counter = 0;
                    for (OptionWrapper ow : fullFieldWrapper.getInputOptionsFields()) {
                        if (ow.getValue()==null || ow.getValue().equals("") || ow.getValue().trim().equals("") || ow.getValue().isEmpty()) {
                            counter++;
                        }
                    }
                    if (counter > 0) {
                        errors.add(new ValidationError("new options", "All added option fields are required to be filled"));
                    }
                }
            }

            if (fullFieldWrapper.getListTypeName() == null) {
                int counter = 0;
                for (OptionWrapper ow : fullFieldWrapper.getInputOptionsFields()) {
                    if (ow.getValue()!=null || !ow.getValue().equals("") || !ow.getValue().trim().equals("") || !ow.getValue().isEmpty()) {
                        counter++;
                    }
                }
                if (counter > 0) {
                    errors.add(new ValidationError("new options", "Not only \"Option group name\" but also option should be filled"));
                }
            }

            if (!((Integer) fullFieldWrapper.getOrderNum() instanceof Integer)) {
                errors.add(new ValidationError("order num", "Order num is set automatically, don't fuck with me"));// TODO remove perhaps
            }

            if (!((Boolean) fullFieldWrapper.isMultipleChoice() instanceof Boolean)) {
                errors.add(new ValidationError("multiple field choice", "Multiple type is set automatically, don't fuck with me"));// TODO remove perhaps
            }
        } else {
            errors.add(new ValidationError("full field", "Field shouldn't be null"));
        }

        return errors;
    }

}
