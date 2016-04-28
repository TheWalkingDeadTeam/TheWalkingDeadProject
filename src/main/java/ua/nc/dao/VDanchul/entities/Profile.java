package ua.nc.dao.VDanchul.entities;

import java.util.List;

/**
 * Created by Rangar on 28.04.2016.
 */
public class Profile {
    private List<ProfileField> fields;

    public List<ProfileField> getFields() {
        return fields;
    }

    public void setFields(List<ProfileField> fields) {
        this.fields = fields;
    }
}
