package ua.nc.validator;

import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;

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

            if (profileField.isMultipleChoice()) {
                validationError = validateMultipleChoiceField(profileField, 1, 3);
            } else {
                validationError = validateNotMultipleChoiceField(profileField);
            }
            if (validationError != null) {
                errors.add(validationError);
                }
        }
            return errors;
        }
    private ValidationError validateNotMultipleChoiceField(ProfileField profileField){
        ValidationError validationError = null;
        if (!profileField.getValues().get(0).getValue().isEmpty()) {
            if(profileField.getFieldType().equals("Value_Date")){
                String datePattern = new String("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\\\d\\\\d)");
                Pattern pattern =  Pattern.compile(datePattern);
                Matcher matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if(!matcher.matches()){
                    validationError = new ValidationError(profileField.getFieldName(),"Please, enter date in correct format");
                }
            }
            if(profileField.getFieldType().equals("Value_Double")){
                String doublePattern = new String("([0-9]+.?[0-9]+)");
                Pattern pattern =  Pattern.compile(doublePattern);
                Matcher matcher = pattern.matcher(profileField.getValues().get(0).getValue());
                if(!matcher.matches()){
                    validationError = new ValidationError(profileField.getFieldName(),"Please, enter number");
                }
            }
            if(profileField.getFieldType().equals("List_Value")){
                if(profileField.getValues().size()!=1){
                    validationError = new ValidationError(profileField.getFieldName(),"You should choose one fields ");
                }

            }
        } else {
            validationError = new ValidationError(profileField.getFieldName(), "Field shouldn't be empty");
        }
        return validationError;
    }
    private ValidationError validateMultipleChoiceField (ProfileField profileField, int min, int max){
        ValidationError validationError = null;
        if (!profileField.getValues().isEmpty()) {
            int size = profileField.getValues().size();
            if (size<min || size>max ) {
                validationError = new ValidationError(profileField.getFieldName(),"You should choose from "+min+" to "+max+" values");
                }
        } else {
            validationError = new ValidationError(profileField.getFieldName(), "You should choose some fields ");
        }
        return validationError;
    }
}
