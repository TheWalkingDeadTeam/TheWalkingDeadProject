package ua.nc.entity.profile;

import ua.nc.entity.Identified;

/**
 * Created by Rangar on 24.04.2016.
 */
public class ListType implements Identified<Integer> {
    private int ID;
    private String name;

    @Override
    public Integer getID() {
        return ID;
    }

    protected void setID(int id) { this.ID = id;  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListType(String name){
        this.name = name;
    }
}
