package ua.nc.entity.ces;

/**
 * Created by Ермоленко on 01.05.2016.
 */
class CESStatus {

    public Integer id;
    public String name;

    CESStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

}
