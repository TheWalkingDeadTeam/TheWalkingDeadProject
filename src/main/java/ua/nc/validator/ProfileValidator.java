package ua.nc.validator;

import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
            }else {
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

        return validationError;
    }
    private ValidationError validateMultipleChoiceField (ProfileField profileField, int min, int max){
        ValidationError validationError = null;
        int size = profileField.getValues().size();
        if (size<min || size>max ) {
        validationError = new ValidationError(profileField.getFieldName(),"You should choose from 2 to 4 values");
        }
        return validationError;
    }
}
