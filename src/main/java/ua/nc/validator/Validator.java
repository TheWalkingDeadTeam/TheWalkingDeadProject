package ua.nc.validator;

import java.util.Set;

/**
 * Created by Pavel on 26.04.2016.
 */
public interface Validator {
    public Set<ValidationError> validate(Object obj);
}
