package ua.nc.entity.profile;

import java.util.Date;

/**
 * Created by Rangar on 24.04.2016.
 */
public class FieldValue  {
    private int fieldID;
    private int applicationID;
    private String valueText;
    private Double valueDouble;
    private Date valueDate;
    private Integer listValueID;

    public Integer getListValueID() {
        return listValueID;
    }

    public void setListValueID(Integer listValueID) {
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

    public Double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    public FieldValue(int fieldID ,int applicationID, String valueText, Double valueDouble, Date valueDate, Integer listValueID){
        this.fieldID = fieldID;
        this.applicationID = applicationID;
        this.valueText = valueText;
        this.valueDouble = valueDouble;
        this.valueDate = valueDate;
        this.listValueID = listValueID;
    }
}
