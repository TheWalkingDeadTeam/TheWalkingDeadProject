package ua.nc.validator;

/**
 * Created by Pavel on 26.04.2016.
 */
public class ValidationError {
    private String field;
    private String errorMessage;

    public ValidationError() {
    }

    public ValidationError(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
