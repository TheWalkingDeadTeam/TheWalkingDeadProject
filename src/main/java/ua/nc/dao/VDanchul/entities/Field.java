package ua.nc.dao.VDanchul.entities;

/**
 * Created by Rangar on 24.04.2016.
 */
public class Field implements Identified<Integer>{
    private int ID;
    public int cesID;
    public String name;
    public FieldType fieldType;
    public boolean multipleChoice;
    public ListType listType;

    @Override
    public Integer getID() {
        return ID;
    }

    protected void setID(int id){
        this.ID = id;

    }
}
