package ua.nc.service;


import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
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

    @Override
    public Profile getProfile(UserDetailsImpl userDetails, int cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        FieldDAO fieldDAO = daoFactory.getFieldDAO(connection);
        FieldTypeDAO fieldTypeDAO = daoFactory.getFieldTypeDAO(connection);
        FieldValueDAO fieldValueDAO = daoFactory.getFieldValueDAO(connection);
        ListValueDAO listValueDAO = daoFactory.getListValueDAO(connection);
        boolean flagApplied = isApplied(userDetails.getId(), cesId);
        Profile result = new Profile();
        List<ProfileField> profileFields = new ArrayList<>();
        List<Field> fields = fieldDAO.getFieldsForCES(cesId);
        for (Field field : fields) {
            ProfileField profileField = new ProfileField();
            profileField.setId(field.getId());
            profileField.setFieldName(field.getName());
            profileField.setOrderNum(field.getOrderNum());
            profileField.setMultipleChoice(field.getMultipleChoice());
            profileField.setFieldType(fieldTypeDAO.read(field.getFieldTypeID()).getName());
            List<ProfileFieldValue> profileFieldValues = new ArrayList<>();
            if (field.getListTypeID() == null) {
                if (!flagApplied) {
                    profileFieldValues.add(new ProfileFieldValue());
                    profileField.setValues(profileFieldValues);
                } else {
                    FieldValue fieldValue = fieldValueDAO.getFieldValueByUserCESField(
                            userDetails.getId(), cesId, field.getId()).iterator().next();
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
                        userDetails.getId(), cesId, field.getId());
                for (ListValue listValue : listValues) {
                    ProfileFieldValue pfValue = new ProfileFieldValue();
                    pfValue.setId(listValue.getId().toString());
                    pfValue.setFieldValueName(listValue.getValueText());
                    if (flagApplied) {
                        boolean matched = false;
                        for (FieldValue fieldValue : fieldValues) {
                            if (listValue.getId().equals(fieldValue.getListValueID())) {
                                pfValue.setValue(Boolean.TRUE.toString());
                                matched = true;
                            }
                        }
                        if (!matched) {
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
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<FieldValue> parseProfile(int applicationId, Profile profile) {
        List<FieldValue> result = new ArrayList<>();
        for (ProfileField profileField : profile.getFields()) {
            switch (profileField.getFieldType()) {
                case "number":
                    result.add(new FieldValue(profileField.getId(), applicationId, null,
                            Double.parseDouble(profileField.getValues().get(0).getValue()), null, null));
                    break;
                case "text":
                case "tel":
                case "textarea":
                    result.add(new FieldValue(profileField.getId(), applicationId,
                            profileField.getValues().get(0).getValue(), null, null, null));
                    break;
                case "date":
                    DateFormat format = new SimpleDateFormat();
                    try {
                        result.add(new FieldValue(profileField.getId(), applicationId, null,
                                null, format.parse(profileField.getValues().get(0).getValue()), null));
                    } catch (ParseException e) {
                        e.printStackTrace(); // say smth about wrong date
                    }
                    break;
                case "select":
                case "checkbox":
                case "radio":
                    if (!profileField.getMultipleChoice()) {
                        result.add(new FieldValue(profileField.getId(), applicationId, null,
                                null, null, Integer.parseInt(profileField.getValues().get(0).getId())));
                    } else {
                        for (ProfileFieldValue pfValue : profileField.getValues()) {
                            result.add(new FieldValue(profileField.getId(), applicationId, null,
                                    null, null, Integer.parseInt(pfValue.getId())));
                        }
                    }
                    break;
            }
        }
        return result;
    }

    private boolean isApplied(int userId, int cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
        Application resultSet = applicationDAO.getApplicationByUserCES(userId, cesId);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (resultSet == null) {
            return false;
        } else {
            return true;
        }

    }

    private void createProfile(Profile profile, int userId, int cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
            FieldValueDAO fieldValueDAO = daoFactory.getFieldValueDAO(connection);
            Application application = applicationDAO.create(new Application(userId, cesId));
            List<FieldValue> fieldValues = parseProfile(application.getId(), profile);
            for (FieldValue fieldValue : fieldValues) {
                fieldValueDAO.create(fieldValue);
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException exp) {
                throw new DAOException(exp);
            }
            throw new DAOException(e);
        }
    }

    private void updateProfile(Profile profile, int userId, int cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
            FieldValueDAO fieldValueDAO = daoFactory.getFieldValueDAO(connection);
            Application application = applicationDAO.getApplicationByUserCES(userId, cesId);
            List<FieldValue> fieldValues = parseProfile(application.getId(), profile);
            List<Integer> multipleFields = new ArrayList<>();
            for (ProfileField profileField : profile.getFields()) {
                if (profileField.getMultipleChoice()) {
                    fieldValueDAO.deleteMultiple(userId, cesId, profileField.getId());
                    multipleFields.add(profileField.getId());
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
                throw new DAOException(exp);
            }
            throw new DAOException(e);
        }
    }

    @Override
    public void setProfile(UserDetailsImpl userDetails, Profile profile) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        CES ces = cesDAO.getCurrentCES();
        if (isApplied(userDetails.getId(), ces.getId())) {
            updateProfile(profile, userDetails.getId(), ces.getId());
        } else {
            createProfile(profile, userDetails.getId(), ces.getId());
        }
    }
}
