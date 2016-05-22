package ua.nc.validator;

import ua.nc.entity.FieldWrapper;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ProfileField;
import ua.nc.entity.profile.ProfileFieldValue;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Neltarion on 16.05.2016.
 */
public class SavePositionValidator implements Validator {

    /**
     * Validates if incoming values are Integer and > 0
     *
     * @param obj Object to validate
     * @return Set of errors if last are present. Else returns empty LinkedHashSet.
     */
    @Override
    public Set<ValidationError> validate(Object obj) {
        FieldWrapper fields = (FieldWrapper) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();

        if (fields.getFields() != null && !fields.getFields().isEmpty()) {
            for (Field f: fields.getFields()) {
                if (!((Integer)f.getOrderNum() instanceof Integer)) {
                    errors.add(new ValidationError("order num", "Position should be an integer..."));
                }
                if (f.getOrderNum() < 0) {
                    errors.add(new ValidationError("order num", "Position should be > 0"));
                }
            }
        } else {
            errors.add(new ValidationError("fields", "Nothing to save"));
        }

        return errors;
    }
}
