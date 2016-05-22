package ua.nc.service;

import ua.nc.entity.FullFieldWrapper;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ListValue;

import java.util.List;

/**
 * Created by Neltarion on 12.05.2016.
 */
public interface EditFormService {

    /**
     * Get all application form fields for CES
     *
     * @param ces_id current enrollment session id
     * @return List of CES Fields or an empty LinkedList.
     */
    public List<Field> getAllFields(Integer ces_id);

    /**
     * Get all list_values for desired list
     *
     * @param listId List id
     * @return List of ListValues for desired List or an empty LinkedList.
     */
    public List<ListValue> getListValues(Integer listId);

    /**
     * Adds new question to CES application form
     *
     * @param field FullField Wrapper object
     * @see FullFieldWrapper
     */
    public void addNewQuestion(FullFieldWrapper field);

    /**
     * Deletes question from disered ES application form
     *
     * @param ces_id   Enrollment Session id
     * @param field_id application form field if
     */
    public void deleteQuestionFromCES(Integer ces_id, Integer field_id);

    /**
     * Updates position of question in student application form
     *
     * @param field Field object with orderNum not null
     */
    public void updatePosition(Field field);

    /**
     * Get current enrollment session id
     *
     * @return CES id
     */
    public Integer getCES_ID();

    /**
     * Generates new position number form question in student application form.
     *
     * @return new position number for question in student application form. Generated number is maximum order number + 1
     */
    public Integer newPositionNumber();

    /**
     * Get Field by id
     *
     * @param id Field id
     * @return Field object
     */
    public Field getField(Integer id);

}
