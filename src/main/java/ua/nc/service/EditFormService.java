package ua.nc.service;

import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ListValue;

import java.util.List;

/**
 * Created by Neltarion on 12.05.2016.
 */
public interface EditFormService{

    public List<Field> getAllFields(Integer ces_id);// TODO pass proper ces_id

    public List<ListValue> getListValues(Integer listId);

    public void addNewQuestion(Field field);

}
