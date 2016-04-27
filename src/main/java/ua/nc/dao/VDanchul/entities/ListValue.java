package ua.nc.dao.VDanchul.entities;

/**
 * Created by Rangar on 24.04.2016.
 */
public class ListValue implements Identified<Integer> {
    private int ID;
    private int listTypeID;
    private double valueDouble;
    private String valueText;

    @Override
    public Integer getID() {
        return ID;
    }

    protected void setID(int id) { this.ID = id;  }

    public int getListTypeID() {
        return listTypeID;
    }

    public void setListTypeID(int listTypeID) {
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
