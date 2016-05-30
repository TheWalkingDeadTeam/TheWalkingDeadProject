package ua.nc.entity;

import java.util.List;

/**
 * Created by Neltarion on 16.05.2016.
 *
 * Wrapper class for Field and accompanying values required to create a new question in student application form
 */
public class FullFieldWrapper {

    private Integer ID;
    private String name;
    private int fieldTypeID;
    private Boolean multipleChoice;
    private int orderNum;
    private Integer listTypeID;
    private String listTypeName;
    private List<OptionWrapper> inputOptionsFields;

    public FullFieldWrapper(String name, int fieldTypeID, boolean multipleChoice, int orderNum, Integer listTypeID, String listTypeName, List<OptionWrapper> inputOptionsFields) {
        this.name = name;
        this.fieldTypeID = fieldTypeID;
        this.multipleChoice = multipleChoice;
        this.orderNum = orderNum;
        this.listTypeID = listTypeID;
        this.listTypeName = listTypeName;
        this.inputOptionsFields = inputOptionsFields;
    }

    public FullFieldWrapper() {
    }

    @Override
    public String toString() {
        return "FullFieldWrapper{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", fieldTypeID=" + fieldTypeID +
                ", multipleChoice=" + multipleChoice +
                ", orderNum=" + orderNum +
                ", listTypeID=" + listTypeID +
                ", listTypeName='" + listTypeName + '\'' +
                ", inputOptionsFields=" + inputOptionsFields.toString() +
                '}';
    }

    public List<OptionWrapper> getInputOptionsFields() {
        return inputOptionsFields;
    }

    public void setInputOptionsFields(List<OptionWrapper> inputOptionsFields) {
        this.inputOptionsFields = inputOptionsFields;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFieldTypeID() {
        return fieldTypeID;
    }

    public void setFieldTypeID(int fieldTypeID) {
        this.fieldTypeID = fieldTypeID;
    }

    public Boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(Boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getListTypeID() {
        return listTypeID;
    }

    public void setListTypeID(Integer listTypeID) {
        this.listTypeID = listTypeID;
    }

    public String getListTypeName() {
        return listTypeName;
    }

    public void setListTypeName(String listTypeName) {
        this.listTypeName = listTypeName;
    }

}
