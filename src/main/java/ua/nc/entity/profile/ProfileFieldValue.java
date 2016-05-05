package ua.nc.entity.profile;

/**
 * Created by Rangar on 28.04.2016.
 */
public class ProfileFieldValue {
    private String ID;
    private String fieldValueName;
    private String value;

    public String getId() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public String getFieldValueName() {
        return fieldValueName;
    }

    public void setFieldValueName(String fieldValueName) {
        this.fieldValueName = fieldValueName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
