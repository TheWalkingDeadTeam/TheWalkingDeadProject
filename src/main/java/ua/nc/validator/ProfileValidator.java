package ua.nc.validator;

import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;
import ua.nc.entity.profile.ProfileFieldValue;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;

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
    private int minNumberOfChoice = 1;
    private int maxNumberOfChoice = 4;
//    private String numbPattern = "([0-5]+.?[0-5]+)";
    private String numbPattern = "/^(0\\.[1-5]|[1-5][0-5]{0,2}(\\.[1-5])?)$/";
    private String telPattern = "^\\+\\d{12}$";
    private Set<ValidationError> errors;
    private static int REGISTRATION_ONGOING_ID = 2;

    @Override
    public Set<ValidationError> validate(Object obj) {
        errors = new LinkedHashSet<>();
        Profile profile = (Profile) obj;

        CESService cesService = new CESServiceImpl();
        Integer status = cesService.getCurrentCES().getStatusId();
        if (status != REGISTRATION_ONGOING_ID) {
            if (status < 2) {
                errors.add(new ValidationError("ces status", "Can't enroll before an interviewing period"));
            } else if (status > 2) {
                errors.add(new ValidationError("ces status", "Can't enroll after an interviewing period"));
            }

        }

        for (ProfileField profileField:profile.getFields() ) {
            ValidationError validationError ;
            if (profileField.getMultipleChoice()) {
                validationError = validateMultipleChoiceField(profileField, minNumberOfChoice, maxNumberOfChoice, profileField.getFieldType());
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
        if (countElement(profileField.getValues(), fieldType) > 0) {
            if (fieldType.equals("number")) {
                pattern = Pattern.compile(numbPattern);
                matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if (matcher.matches()) {
                    return  new ValidationError(profileField.getFieldName(), "Please, enter number from 1 to 5");
                }
            }

            if (fieldType.equals("radio") || fieldType.equals("select")) {
                if (countElement(profileField.getValues(), fieldType) != 1) {
                    return new ValidationError(profileField.getFieldName(), "You should choose one field ");
                }
            }

            if (fieldType.equals("tel")) {
                pattern = Pattern.compile(telPattern);
                matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if (!matcher.matches()) {
                    return new ValidationError(profileField.getFieldName(), "Please, enter phone number");
                }
            }
        } else {
            return new ValidationError(profileField.getFieldName(), "Field shouldn't be empty");
        }
        return null;
    }

    private ValidationError validateMultipleChoiceField(ProfileField profileField, int min, int max, String fieldType) {
        int count = countElement(profileField.getValues(), fieldType);
        if (count < min || count > max) {
            return new ValidationError(profileField.getFieldName(), "You should choose from " + min + " to " + max + " values");
        }else {
            return null;
        }
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

    public int getMinNumberOfChoice() {
        return minNumberOfChoice;
    }

    public void setMinNumberOfChoice(int minNumberOfChoice) {
        this.minNumberOfChoice = minNumberOfChoice;
    }

    public int getMaxNumberOfChoice() {
        return maxNumberOfChoice;
    }

    public void setMaxNumberOfChoice(int maxNumberOfChoice) {
        this.maxNumberOfChoice = maxNumberOfChoice;
    }
}
