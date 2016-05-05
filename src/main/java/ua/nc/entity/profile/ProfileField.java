package ua.nc.entity.profile;

import java.util.List;

/**
 * Created by Rangar on 28.04.2016.
 */
public class ProfileField {
    private int ID;
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

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
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

    public boolean getMultipleChoice() {
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
