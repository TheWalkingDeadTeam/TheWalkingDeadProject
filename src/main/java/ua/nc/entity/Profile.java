package ua.nc.entity;

import ua.nc.entity.profile.Field;

import java.util.List;

/**
 * Created by Pavel on 28.04.2016.
 */
public class Profile {
    List<ProfileField> fields;

    public List<ProfileField> getFields() {
        return fields;
    }

    public void setFields(List<ProfileField> fields) {
        this.fields = fields;
    }
}
