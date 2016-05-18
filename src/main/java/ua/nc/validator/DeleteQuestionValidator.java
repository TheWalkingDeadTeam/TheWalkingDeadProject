package ua.nc.validator;

import ua.nc.entity.ListWrapper;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Neltarion on 14.05.2016.
 */
public class DeleteQuestionValidator implements Validator {
    @Override
    public Set<ValidationError> validate(Object obj) {
        ListWrapper values = (ListWrapper) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();

        if (!values.getId().isEmpty() && values != null) {
            for (Integer id: values.getId()) {
                if (!(id instanceof Integer)) {
                    errors.add(new ValidationError("id", "ID should be an integer, sorry :)"));
                }
            }
        } else {
            errors.add(new ValidationError("id", "Nothing selected to delete"));
        }

        return errors;
    }
}
