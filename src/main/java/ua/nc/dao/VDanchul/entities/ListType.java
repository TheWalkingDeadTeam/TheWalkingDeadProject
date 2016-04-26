package ua.nc.dao.VDanchul.entities;

/**
 * Created by Rangar on 24.04.2016.
 */
public class ListType implements Identified<Integer> {
    public int ID;
    public String name;

    @Override
    public Integer getID() {
        return ID;
    }
}
