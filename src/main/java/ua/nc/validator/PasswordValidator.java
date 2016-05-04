package ua.nc.validator;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * Created by Alexander on 29.04.2016.
 */
public class PasswordValidator implements Validator {

    private String PASSWORD_PATTERN = "[а-яА-ЯёЁa-zA-Z0-9!@#$%^&*()+|~\\-=‘{}\\[\\]:\\\";’<>\\?,.\\/_\\\\s-]*";
    private Pattern pattern;
    private Matcher matcher;

    /**
     * Validate password during it changing
     * Separate class soon will be fully completed
     *
     * @param obj - String object
     * @return
     */
    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new LinkedHashSet<>();
        String password = (String) obj;
        if (!(password != null && password.isEmpty())) {
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                errors.add(new ValidationError("password", "Enter correct password"));
            }
            if (password.length() < 6 || password.length() > 60) {
                errors.add(new ValidationError("password", "Password length should be between 6 and 32 "));
            }
        } else {
            errors.add(new ValidationError("password", "Password shouldn't be empty"));
        }
        return errors;
    }
}
