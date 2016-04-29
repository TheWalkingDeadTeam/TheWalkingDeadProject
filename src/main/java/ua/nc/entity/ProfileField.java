package ua.nc.entity;

import java.util.List;

/**
 * Created by Pavel on 28.04.2016.
 */
public class ProfileField {
    private Integer id;
    private String name;
    private Integer orderNumber;
    private Boolean multiple;
    private String type;
    List<ProfileFieldValue> values;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Boolean getMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ProfileFieldValue> getValues() {
        return values;
    }

    public void setValues(List<ProfileFieldValue> values) {
        this.values = values;
    }
}
