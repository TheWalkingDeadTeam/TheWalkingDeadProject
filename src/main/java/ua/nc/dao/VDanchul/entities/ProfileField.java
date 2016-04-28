package ua.nc.dao.VDanchul.entities;

import java.util.List;

/**
 * Created by Rangar on 28.04.2016.
 */
public class ProfileField {
    private String ID;
    private String fieldName;
    private int orderNum;
    private boolean multipleChoice;
    private String fieldType;

    private List<ProfileFieldValue> values;

    public List<ProfileFieldValue> getValues() {
        return values;
    }

    public void setValues(List<ProfileFieldValue> values) {
        this.values = values;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String name) {
        this.fieldName = name;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String type) {
        this.fieldType = type;
    }
}
