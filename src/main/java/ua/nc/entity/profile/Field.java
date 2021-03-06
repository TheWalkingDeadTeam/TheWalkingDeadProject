package ua.nc.entity.profile;

import ua.nc.entity.Identified;

import javax.mail.search.IntegerComparisonTerm;

/**
 * Created by Rangar on 24.04.2016.
 */
public class Field implements Identified<Integer> {
    private Integer ID;
    private String name;
    private int fieldTypeID;
    private boolean multipleChoice;
    private int orderNum;
    private Integer listTypeID;

    public Field(String name, int fieldTypeID, boolean multipleChoice, int orderNum, Integer listTypeID) {
        this.name = name;
        this.fieldTypeID = fieldTypeID;
        this.multipleChoice = multipleChoice;
        this.orderNum = orderNum;
        this.listTypeID = listTypeID;
    }
    public Field(){}

    @Override
    public Integer getId() {
        return ID;
    }

    protected void setId(int id) {
        this.ID = id;
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

    public boolean getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public Integer getListTypeID() {
        return listTypeID;
    }

    public void setListTypeID(Integer listTypeID) {
        this.listTypeID = listTypeID;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
