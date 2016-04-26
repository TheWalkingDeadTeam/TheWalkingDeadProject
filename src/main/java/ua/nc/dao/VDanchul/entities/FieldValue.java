package ua.nc.dao.VDanchul.entities;

import java.util.Date;

/**
 * Created by Rangar on 24.04.2016.
 */
public class FieldValue implements Identified<Integer> {
    public int ID;
    public Field field;
    public int applicationID;
    public String valueText;
    public double valueDouble;
    public Date valueDate;
    public int listValueID;

    @Override
    public Integer getID() {
        return ID;
    }
}
