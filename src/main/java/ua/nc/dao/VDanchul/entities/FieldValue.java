package ua.nc.dao.VDanchul.entities;

import java.util.Date;

/**
 * Created by Rangar on 24.04.2016.
 */
public class FieldValue  {
    private int fieldID;
    private int applicationID;
    private String valueText;
    private double valueDouble;
    private Date valueDate;
    private int listValueID;

    public int getListValueID() {
        return listValueID;
    }

    public void setListValueID(int listValueID) {
        this.listValueID = listValueID;
    }

    public int getFieldID() {
        return fieldID;
    }

    public void setFieldID(int fieldID) {
        this.fieldID = fieldID;
    }

    public int getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(int applicationID) {
        this.applicationID = applicationID;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

}
