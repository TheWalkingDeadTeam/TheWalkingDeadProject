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
        ProfileField field = null;
        Iterator<ProfileField> profileFieldIterator = fields.iterator();
        while (profileFieldIterator.hasNext()){
            field = profileFieldIterator.next();
            ValidationError validationError = validateField(field);
            if(validationError!=null){
             errors.add(validationError);
            }
        }
        return errors;
    }

    private ValidationError validateField(ProfileField field){
        ValidationError validationError = null;







        return validationError;
    }
}
