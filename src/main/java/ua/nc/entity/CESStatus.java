package ua.nc.entity;

/**
 * Created by Ермоленко on 01.05.2016.
 */
public class CESStatus implements Identified<Integer> {

    private int id;
    private String name;

    public CESStatus(String name) {
        this.name = name;
    }
    public CESStatus(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }
}
