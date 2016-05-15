package ua.nc.entity;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public class StudentStatus {
    private String type;
    private List<Integer> values;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "StudentStatus{" +
                "type='" + type + '\'' +
                ", values=" + values +
                "}, values size = " + values.size();
    }
}
