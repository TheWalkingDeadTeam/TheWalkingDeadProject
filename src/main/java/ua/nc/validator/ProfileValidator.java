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
    private String telPattern = new String("^\\\\+380\\\\d{9}$");
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
                validationError = validateMultipleChoiceField(profileField, 1, 3);
            } else {
                validationError = validateNotMultipleChoiceField(profileField, profileField.getFieldType());
            }
            if (validationError != null) {
                errors.add(validationError);
            }
        }
        return errors;
    }

    private ValidationError validateNotMultipleChoiceField(ProfileField profileField, String fildType) {
        ValidationError validationError = null;
        if (countElement(profileField.getValues()) > 0) {
            if (fildType.equals("number")) {
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
            if (fildType.equals("radio") || fildType.equals("select")) {
                if (countElement(profileField.getValues()) != 1) {
                    validationError = new ValidationError(profileField.getFieldName(), "You should choose one fields ");
                }
            }

            if (fildType.equals("tel")) {
                pattern = Pattern.compile(numbPattern);
                matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if (!matcher.matches()) {
                    validationError = new ValidationError(profileField.getFieldName(), "Please, enter number");
                }
            }
        } else {
            validationError = new ValidationError(profileField.getFieldName(), "Field shouldn't be empty");
        }
        return validationError;
    }

    private ValidationError validateMultipleChoiceField(ProfileField profileField, int min, int max) {
        ValidationError validationError = null;
        int count = countElement(profileField.getValues());
        if (count < min || count > max) {
            validationError = new ValidationError(profileField.getFieldName(), "You should choose from " + min + " to " + max + " values");
        }
        return validationError;
    }

    private int countElement(List<ProfileFieldValue> values) {
        int count = 0;
        Iterator<ProfileFieldValue> profileFieldIterator = values.iterator();
        while (profileFieldIterator.hasNext()) {
            if (profileFieldIterator.next().getValue().equals("true")) {
                count++;
            }
        }
        return count;
    }
}
