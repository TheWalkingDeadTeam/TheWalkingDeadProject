package ua.nc.dao.VDanchul.entities;

/**
 * Created by Rangar on 28.04.2016.
 */
public class ProfileFieldValue {
    private int ID;
    private String fieldValueName;
    private String value;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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
