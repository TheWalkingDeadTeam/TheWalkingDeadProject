package ua.nc.validator;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pavel on 23.05.2016.
 */
public class ReportExecuteValidator implements Validator {
    private String SQL_UDI_PATTERN = "^.*?(update|delete|insert).*$";
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public Set<ValidationError> validate(Object obj) {
        String sql = (String) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();
        if (!(sql != null && sql.isEmpty())) {
            pattern = Pattern.compile(SQL_UDI_PATTERN);
            matcher = pattern.matcher(sql);
            if (matcher.matches()) {
                errors.add(new ValidationError("sql_query", "Cant update, delete, insert in query"));
            }
        } else {
            errors.add(new ValidationError("sql_query", "Query shouldn't be empty"));
        }
        return errors;
    }
}
