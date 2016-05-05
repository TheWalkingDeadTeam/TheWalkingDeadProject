package ua.nc.service;


import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationDAO;
import ua.nc.dao.postgresql.profile.PostgreFieldValueDAO;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
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
    private CESDAO cesDAO;

    @Override
    public Profile getProfile(UserDetailsImpl userDetails, int cesID) throws DAOException {
        boolean flagApplied = isApplied(userDetails.getID(), cesID);
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
            List<ProfileFieldValue> profileFieldValues = new ArrayList<>();
            if(field.getListTypeID() == null){
                if(!flagApplied){
                    profileFieldValues.add(new ProfileFieldValue());
                    profileField.setValues(profileFieldValues);
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
                    profileFieldValues.add(pfValue);
                    profileField.setValues(profileFieldValues);
                }
            } else {
                List<ListValue> listValues = listValueDAO.getAllListListValue(field.getListTypeID());
                List<FieldValue> fieldValues = fieldValueDAO.getFieldValueByUserCESField(
                        userDetails.getID(), cesID, field.getID());
                for (ListValue listValue : listValues){
                    ProfileFieldValue pfValue = new ProfileFieldValue();
                    pfValue.setID(listValue.getID().toString());
                    pfValue.setFieldValueName(listValue.getValueText());
                    if (flagApplied) {
                        boolean matched = false;
                        for (FieldValue fieldValue : fieldValues) {
                            if (listValue.getID().equals(fieldValue.getListValueID())) {
                                pfValue.setValue(Boolean.TRUE.toString());
                                matched = true;
                            }
                        }
                        if (!matched){
                            pfValue.setValue(Boolean.FALSE.toString());
                        }
                    } else {
                        pfValue.setValue(Boolean.FALSE.toString());
                    }
                    profileFieldValues.add(pfValue);
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

    private boolean isApplied(int userID, int cesID) throws DAOException {
        Application resultSet = applicationDAO.getApplicationByUserCES(userID, cesID);
        if (resultSet == null){
            return false;
        } else {
            return true;
        }

    }

    private void createProfile(Profile profile, int userID, int cesID) throws DAOException {
        Connection connection = daoFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            applicationDAO = new PostgreApplicationDAO(connection);
            Application application = applicationDAO.create(new Application(userID, cesID));
            List<FieldValue> fieldValues = parseProfile(application.getID(), profile);
            fieldValueDAO = new PostgreFieldValueDAO(connection);
            for (FieldValue fieldValue : fieldValues) {
                fieldValueDAO.create(fieldValue);
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException exp) {
                throw  new DAOException(exp);
            }
            throw new DAOException(e);
        }
    }

    private void updateProfile(Profile profile, int userID, int cesID) throws DAOException {
        Connection connection = daoFactory.getConnection();
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
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException exp) {
                throw  new DAOException(exp);
            }
            throw new DAOException(e);
        }
    }

    @Override
    public void setProfile(UserDetailsImpl userDetails, Profile profile) throws DAOException {
        CES ces = cesDAO.getCurrentCES();
        if (isApplied(userDetails.getID(), ces.getID())){
            updateProfile(profile, userDetails.getID(), ces.getID());
        } else {
            createProfile(profile, userDetails.getID(), ces.getID());
        }
    }
}
