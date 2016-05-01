package ua.nc.service;


import ua.nc.dao.*;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.factory.type.DataBaseType;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.profile.PostgreFieldValueDAO;
import ua.nc.entity.Application;
import ua.nc.entity.profile.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 28.04.2016.
 */
public class ProfileServiceImpl implements ProfileService {
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private FieldDAO fieldDAO;
    private GenericDAO<FieldType, Integer> fieldTypeDAO;
    private FieldValueDAO fieldValueDAO;
    private ListValueDAO listValueDAO;
    private ApplicationDAO applicationDAO;

    @Override
    public Profile getProfile(UserDetailsImpl userDetails, int cesID) throws DAOException {
        Profile result = new Profile();
        List<ProfileField> profileFields = new ArrayList<>();
        List<Field> fields = fieldDAO.getFieldsForCES(cesID);
        for(Field field : fields){
            ProfileField profileField = new ProfileField();
            profileField.setID(field.getID());
            profileField.setFieldName(field.getName());
            profileField.setOrderNum(field.getOrderNum());
            profileField.setMultipleChoice(field.getMultipleChoice());
            profileField.setFieldType(fieldTypeDAO.read(field.getFieldTypeID()).getName());
            List<ProfileFieldValue> tempValues = new ArrayList<>();
            if(field.getListTypeID() == null){
                if(!isApplied(userDetails.getID(), cesID)){
                    tempValues.add(new ProfileFieldValue());
                    profileField.setValues(tempValues);
                } else {
                    FieldValue fieldValue = fieldValueDAO.getFieldValueByUserCESField(
                            userDetails.getID(), cesID, field.getID()).iterator().next();
                    ProfileFieldValue pfValue = new ProfileFieldValue();
                    if (fieldValue.getValueText() != null) {
                        pfValue.setValue(fieldValue.getValueText());
                    } else if (fieldValue.getValueDouble() != null) {
                        pfValue.setValue(fieldValue.getValueDouble().toString());
                    } else if (fieldValue.getValueDate() != null) {
                        pfValue.setValue(fieldValue.getValueDate().toString());
                    }
                    tempValues.add(pfValue);
                    profileField.setValues(tempValues);
                }
            } else {
                List<ListValue> listValues = listValueDAO.getAllListListValue(field.getListTypeID());
                List<FieldValue> fieldValues = fieldValueDAO.getFieldValueByUserCESField(
                        userDetails.getID(), cesID, field.getID());
                for (ListValue listValue : listValues){
                    ProfileFieldValue temp = new ProfileFieldValue();
                    temp.setID(listValue.getID().toString());
                    temp.setFieldValueName(listValue.getValueText());
                    if (isApplied(userDetails.getID(), cesID)) {
                        for (FieldValue fieldValue : fieldValues) {
                            if (listValue.getID().equals(fieldValue.getListValueID())) {
                                temp.setValue(Boolean.TRUE.toString());
                            }
                        }
                    } else {
                        temp.setValue(Boolean.FALSE.toString());
                    }
                    tempValues.add(temp);
                }
            }
            profileFields.add(profileField);
        }
        result.setFields(profileFields);
        return result;
    }

    private List<FieldValue> parseProfile(int applicationID, Profile profile) {
        List<FieldValue> result = new ArrayList<>();
        for (ProfileField profileField : profile.getFields()) {
            switch (profileField.getFieldType()) {
                case "number":
                    result.add(new FieldValue(profileField.getID(), applicationID, null,
                            Double.parseDouble(profileField.getValues().get(0).getValue()), null, null));
                    break;
                case "text":
                case "tel":
                case "textarea":
                    result.add(new FieldValue(profileField.getID(), applicationID,
                            profileField.getValues().get(0).getValue(), null, null, null));
                    break;
                case "date":
                    DateFormat format = new SimpleDateFormat();
                    try {
                        result.add(new FieldValue(profileField.getID(), applicationID, null,
                                null, format.parse(profileField.getValues().get(0).getValue()), null));
                    } catch (ParseException e) {
                        e.printStackTrace(); // say smth about wrong date
                    }
                    break;
                case "select":
                case "checkbox":
                case "radio":
                    if (!profileField.getMultipleChoice()){
                        result.add(new FieldValue(profileField.getID(), applicationID, null,
                                null, null, Integer.parseInt(profileField.getValues().get(0).getID())));
                    } else {
                        for (ProfileFieldValue pfValue : profileField.getValues()){
                            result.add(new FieldValue(profileField.getID(), applicationID, null,
                                    null, null, Integer.parseInt(pfValue.getID())));
                        }
                    }
                    break;
            }
        }
        return result;
    }

    private boolean isApplied(int userID, int cesID){
        Application resultSet = applicationDAO.getApplicationByUserCES(userID, cesID);
        if (resultSet == null){
              return false;
        } else {
            return true;
        }

    }

    private void createProfile(Profile profile, int userID, int cesID) throws DAOException, SQLException {
        Connection connection = null;
        try {
            // take from pool
            connection.setAutoCommit(false);
            applicationDAO = new PostgreApplicationDAO(connection);
            Application application = applicationDAO.create(new Application(userID, cesID));
            List<FieldValue> fieldValues = parseProfile(application.getID(), profile);
            fieldValueDAO = new PostgreFieldValueDAO(connection);
            for (FieldValue fieldValue : fieldValues) {
                fieldValueDAO.create(fieldValue);
            }
        } catch (Exception e) {
            connection.rollback();
            throw new DAOException(e);
        }
    }

    private void updateProfile(Profile profile, int userID, int cesID) throws SQLException, DAOException {
        Connection connection = null; // take from pool
        try {
            connection.setAutoCommit(false);
            applicationDAO = new PostgreApplicationDAO(connection);
            Application application = applicationDAO.getApplicationByUserCES(userID, cesID);
            List<FieldValue> fieldValues = parseProfile(application.getID(), profile);
            List<Integer> multipleFields = new ArrayList<>();
            for (ProfileField profileField : profile.getFields()) {
                if (profileField.getMultipleChoice()) {
                    fieldValueDAO.deleteMultiple(userID, cesID, profileField.getID());
                    multipleFields.add(profileField.getID());
                }
            }
            for (FieldValue fieldValue : fieldValues) {
                if (!multipleFields.contains(fieldValue.getFieldID())) {
                    fieldValueDAO.update(fieldValue);
                } else {
                    fieldValueDAO.create(fieldValue);
                }
            }
        } catch (Exception e) {
            connection.rollback();
            throw new DAOException(e);
        }
    }

    @Override
    public void setProfile(UserDetailsImpl userDetails, int cesID, Profile profile) throws DAOException, SQLException {
        if (isApplied(userDetails.getID(), cesID)){
            updateProfile(profile, userDetails.getID(), cesID);
        } else {
            createProfile(profile, userDetails.getID(), cesID);
        }
    }
}
