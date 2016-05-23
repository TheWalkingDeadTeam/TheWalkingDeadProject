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
    private Pattern pattern;
    private Matcher matcher;

    private static void nameCheck(FullFieldWrapper fullFieldWrapper, Set<ValidationError> errors, Pattern pattern, Matcher matcher) {
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
    }

    private static void fieldTypeCheck(FullFieldWrapper fullFieldWrapper, Set<ValidationError> errors) {
        if (fullFieldWrapper.getFieldTypeID() <= 0 || fullFieldWrapper.getFieldTypeID() > 8) {//TODO replace digit with proper method
            errors.add(new ValidationError("field type", "Choose correct type"));
        } else {
            if (!(((Integer) fullFieldWrapper.getFieldTypeID()) instanceof Integer)) {
                errors.add(new ValidationError("field type", "Choose correct type, Mr.Hacker"));
            }
        }
    }

    private static void newOptionsCheck(FullFieldWrapper fullFieldWrapper, Set<ValidationError> errors) {
        if (!fullFieldWrapper.getListTypeName().isEmpty() || !fullFieldWrapper.getListTypeName().equals("") || !fullFieldWrapper.getListTypeName().trim().equals("")) {
            if (fullFieldWrapper.getInputOptionsFields() == null || fullFieldWrapper.getInputOptionsFields().isEmpty()) {
                errors.add(new ValidationError("new options", "If select, radio or checkbox is chosen, both - \"Option group name\" field and option fields must be filled"));
            } else {
                int counter = 0;
                for (OptionWrapper ow : fullFieldWrapper.getInputOptionsFields()) {
                    if (ow.getValue() == null || ow.getValue().equals("") || ow.getValue().trim().equals("") || ow.getValue().isEmpty()) {
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
                if (ow.getValue() != null || !ow.getValue().equals("") || !ow.getValue().trim().equals("") || !ow.getValue().isEmpty()) {
                    counter++;
                }
            }
            if (counter > 0) {
                errors.add(new ValidationError("new options", "Not only \"Option group name\" but also option should be filled"));
            }
        }
    }

    private static void orderCheck(FullFieldWrapper fullFieldWrapper, Set<ValidationError> errors) {
        if (!((Integer) fullFieldWrapper.getOrderNum() instanceof Integer)) {
            errors.add(new ValidationError("order num", "Order num is set automatically, good try :)"));
        }
    }

    private static void isMultipleCheck(FullFieldWrapper fullFieldWrapper, Set<ValidationError> errors) {
        if (!((Boolean) fullFieldWrapper.isMultipleChoice() instanceof Boolean)) {
            errors.add(new ValidationError("multiple field choice", "Multiple type is set automatically, good try :)"));
        }
    }

    /**
     * Validates FullFieldWrapper object by its fields:
     * <li>name - must match [^.!?\s][^.!?]*(?:[.!?](?!['"]?\s|$)[^.!?]*)*[.!?]?['"]?(?=\s|$) pattern and be from 5 to 100 characters in length</li>
     * <li>field type - must match existing type</li>
     * <li>new options - checks for consistency. Group name and new options must be filled simultaneously</li>
     * <li>order number - must be an Integer</li>
     * <li>multiple choice - must be Boolean</li>
     *
     * @param obj Object to validate
     * @return Set of errors if last are present. Else returns empty LinkedHashSet.
     */
    @Override
    public Set<ValidationError> validate(Object obj) {

        FullFieldWrapper fullFieldWrapper = (FullFieldWrapper) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();
//        fullFieldWrapper.getInputOptionsFields();

        if (fullFieldWrapper != null) {

            // check name validity
            nameCheck(fullFieldWrapper, errors, pattern, matcher);

            // check field type validity
            fieldTypeCheck(fullFieldWrapper, errors);

            // new options consistency and validity check
            newOptionsCheck(fullFieldWrapper, errors);

            // check order number for validity
            orderCheck(fullFieldWrapper, errors);

            // check multiple choice for validity
            isMultipleCheck(fullFieldWrapper, errors);

        } else {
            errors.add(new ValidationError("full field", "Field shouldn't be null"));
        }

        return errors;
    }

}
