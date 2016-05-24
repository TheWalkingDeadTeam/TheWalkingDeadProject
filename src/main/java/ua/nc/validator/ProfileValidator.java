package ua.nc.validator;

import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;
import ua.nc.entity.profile.ProfileFieldValue;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pavel on 28.04.2016.
 */
public class ProfileValidator implements Validator {
    private Pattern pattern;
    private Matcher matcher;
    private String numbPattern = new String("([0-9]+.?[0-9]+)");
    private String telPattern = new String("^\\+380\\d{9}$");
    private Set<ValidationError> errors;

    @Override
    public Set<ValidationError> validate(Object obj) {
        errors = new LinkedHashSet<>();
        Profile profile = (Profile) obj;
        List<ProfileField> fields = profile.getFields();
        ProfileField profileField = null;
        Iterator<ProfileField> profileFieldIterator = fields.iterator();
        while (profileFieldIterator.hasNext()) {
            ValidationError validationError = null;
            profileField = profileFieldIterator.next();

            if (profileField.getMultipleChoice()) {
                validationError = validateMultipleChoiceField(profileField, 1, 3, profileField.getFieldType());// TODO dafuck are this numbers??
            } else {
                validationError = validateNotMultipleChoiceField(profileField, profileField.getFieldType());
            }
            if (validationError != null) {
                errors.add(validationError);
            }
        }
        return errors;
    }

    private ValidationError validateNotMultipleChoiceField(ProfileField profileField, String fieldType) {
        ValidationError validationError = null;
        if (countElement(profileField.getValues(), fieldType) > 0) {
            if (fieldType.equals("number")) {
                pattern = Pattern.compile(numbPattern);
                matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if (matcher.matches()) {
                    validationError = new ValidationError(profileField.getFieldName(), "Please, enter number");
                } else {
                    double value = Double.parseDouble(profileField.getValues().get(0).getValue());
                    if (value < 0 || value > 10) {
                        validationError = new ValidationError(profileField.getFieldName(), "Please, enter number between 0 and 10");
                    }
                }
            }
            if (fieldType.equals("radio") || fieldType.equals("select")) {
                if (countElement(profileField.getValues(), fieldType) != 1) {
                    validationError = new ValidationError(profileField.getFieldName(), "You should choose one field ");
                }
            }

            if (fieldType.equals("tel")) {
                pattern = Pattern.compile(telPattern);
                matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if (!matcher.matches()) {
                    validationError = new ValidationError(profileField.getFieldName(), "Please, enter phone number");
                }
            }
        } else {
            validationError = new ValidationError(profileField.getFieldName(), "Field shouldn't be empty");
        }
        return validationError;
    }

    private ValidationError validateMultipleChoiceField(ProfileField profileField, int min, int max, String fieldType) {
        ValidationError validationError = null;
        int count = countElement(profileField.getValues(), fieldType);
        if (count < min || count > max) {
            validationError = new ValidationError(profileField.getFieldName(), "You should choose from " + min + " to " + max + " values");
        }
        return validationError;
    }

    private int countElement(List<ProfileFieldValue> values, String fieldType) {
        int count = 0;
        Iterator<ProfileFieldValue> profileFieldIterator = values.iterator();
        while (profileFieldIterator.hasNext()) {
            String currentValue = profileFieldIterator.next().getValue();
            if (fieldType.equals("radio") || fieldType.equals("checkbox") || fieldType.equals("select")) {
                if (currentValue.equals("true")) {
                    count++;
                }
            } else {
                if (currentValue != null && !currentValue.trim().equals("") && !currentValue.equals("") && !currentValue.equals(" ")) {
                    count++;
                }
            }
        }
        return count;
    }
}
