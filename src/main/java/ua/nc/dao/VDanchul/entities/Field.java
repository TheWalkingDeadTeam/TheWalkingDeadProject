package ua.nc.dao.VDanchul.entities;

/**
 * Created by Rangar on 24.04.2016.
 */
public class Field implements Identified<Integer>{
    private int ID;
    private int cesID;
    private String name;
    private int fieldTypeID;
    private boolean multipleChoice;
    private int orderNum;
    private int listTypeID;

    @Override
    public Integer getID() {
        return ID;
    }

    protected void setID(int id){
        this.ID = id;
    }

    public int getCesID() {
        return cesID;
    }

    public void setCesID(int cesID) {
        this.cesID = cesID;
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

    public int getListTypeID() {
        return listTypeID;
    }

    public void setListTypeID(int listTypeID) {
        this.listTypeID = listTypeID;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Field(int cesID, String name, int fieldTypeID, boolean multipleChoice, int orderNum, int listTypeID){
        this.cesID = cesID;
        this.name = name;
        this.fieldTypeID = fieldTypeID;
        this.multipleChoice = multipleChoice;
        this.orderNum = orderNum;
        this.listTypeID = listTypeID;
    }
}