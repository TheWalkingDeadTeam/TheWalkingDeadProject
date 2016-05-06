package ua.nc.entity.profile;

import ua.nc.entity.Identified;

/**
 * Created by Rangar on 24.04.2016.
 */
public class ListValue implements Identified<Integer> {
    private int ID;
    private int listTypeID;
    private double valueDouble;
    private String valueText;

    public ListValue(int listTypeID, double valueDouble, String valueText) {
        this.listTypeID = listTypeID;
        this.valueDouble = valueDouble;
        this.valueText = valueText;
    }
    public ListValue(){}

    @Override
    public Integer getId() {
        return ID;
    }

    protected void setId(int id) {
        this.ID = id;
    }

    public int getListID() {
        return listTypeID;
    }

    public void setListID(int listTypeID) {
        this.listTypeID = listTypeID;
    }

    public double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }
}
