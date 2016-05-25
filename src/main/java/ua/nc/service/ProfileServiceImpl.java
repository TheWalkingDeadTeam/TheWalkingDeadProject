package ua.nc.service;


import org.apache.log4j.Logger;
import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Application;
import ua.nc.entity.CES;
import ua.nc.entity.Role;
import ua.nc.entity.User;
import ua.nc.entity.profile.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Pavel on 28.04.2016.
 */
public class ProfileServiceImpl implements ProfileService {
    private final static Logger LOGGER = Logger.getLogger(FeedbackServiceImpl.class);

    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private static final Set<String> typesToDeny = new HashSet<>(Arrays.asList("textarea", "tel", "checkbox"));
    private static final String ROLE_STUDENT = "ROLE_STUDENT";

    @Override
    public Profile getProfile(int userId, int cesId) throws DAOException {
        Connection connection = daoFactory.getConnection();
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        UserService userService = new UserServiceImpl();
        User user = userService.getUser(userId);
        Set<Role> roles = user.getRoles();
        Profile result = new Profile();
        try {
            Role roleStudent = roleDAO.findByName(ROLE_STUDENT);
            boolean contains = roles.contains(roleStudent);
            if (!contains) {
                throw new DAOException("Wrong role");
            }
            FieldDAO fieldDAO = daoFactory.getFieldDAO(connection);
            FieldTypeDAO fieldTypeDAO = daoFactory.getFieldTypeDAO(connection);
            FieldValueDAO fieldValueDAO = daoFactory.getFieldValueDAO(connection);
            ListValueDAO listValueDAO = daoFactory.getListValueDAO(connection);
            boolean flagApplied = isApplied(userId, cesId);
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
                                userId, cesId, field.getId()).iterator().next();
                        ProfileFieldValue pfValue = setProfileFieldValue(fieldValue);
                        profileFieldValues.add(pfValue);
                        profileField.setValues(profileFieldValues);
                    }
                } else {
                    List<ListValue> listValues = listValueDAO.getAllListListValue(field.getListTypeID());
                    List<FieldValue> fieldValues = fieldValueDAO.getFieldValueByUserCESField(
                            userId, cesId, field.getId());
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
                    profileField.setValues(profileFieldValues); ///
                }
                profileFields.add(profileField);
            }
            result.setFields(profileFields);
        } catch (DAOException e) {
            LOGGER.error(e.getCause());
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
        return result;
    }



    private ProfileFieldValue setProfileFieldValue(FieldValue fieldValue) {
        ProfileFieldValue pfValue = new ProfileFieldValue();
        if (fieldValue.getValueText() != null) {
            pfValue.setValue(fieldValue.getValueText());
        } else if (fieldValue.getValueDouble() != null) {
            pfValue.setValue(fieldValue.getValueDouble().toString());
        } else if (fieldValue.getValueDate() != null) {
            pfValue.setValue(fieldValue.getValueDate().toString());
        }
        return pfValue;
    }

    @Override
    public Profile getShortProfile(int userId, int cesId) {
        Connection connection = daoFactory.getConnection();
        FieldDAO fieldDAO = daoFactory.getFieldDAO(connection);
        FieldTypeDAO fieldTypeDAO = daoFactory.getFieldTypeDAO(connection);
        FieldValueDAO fieldValueDAO = daoFactory.getFieldValueDAO(connection);
        ListValueDAO listValueDAO = daoFactory.getListValueDAO(connection);
        Profile result = new Profile();
        List<ProfileField> profileFields = new ArrayList<>();
        try {
            List<Field> fields = fieldDAO.getFieldsForCES(cesId);
            for (Field field : fields) {
                if (!typesToDeny.contains(fieldTypeDAO.read(field.getFieldTypeID()).getName())) {
                    ProfileField profileField = new ProfileField();
                    profileField.setId(field.getId());
                    profileField.setFieldName(field.getName());
                    profileField.setFieldType(fieldTypeDAO.read(field.getFieldTypeID()).getName());
                    List<ProfileFieldValue> profileFieldValues = new ArrayList<>();
                    if (field.getListTypeID() == null) {
                        FieldValue fieldValue = fieldValueDAO.getFieldValueByUserCESField(
                                userId, cesId, field.getId()).iterator().next();
                        ProfileFieldValue pfValue = setProfileFieldValue(fieldValue);
                        profileFieldValues.add(pfValue);
                        profileField.setValues(profileFieldValues);
                    } else {
                        List<ListValue> listValues = listValueDAO.getAllListListValue(field.getListTypeID());
                        List<FieldValue> fieldValues = fieldValueDAO.getFieldValueByUserCESField(
                                userId, cesId, field.getId());
                        for (ListValue listValue : listValues) {
                            ProfileFieldValue pfValue = new ProfileFieldValue();
                            pfValue.setId(listValue.getId().toString());
                            pfValue.setFieldValueName(listValue.getValueText());
                            for (FieldValue fieldValue : fieldValues) {
                                if (listValue.getId().equals(fieldValue.getListValueID())) {
                                    pfValue.setValue(Boolean.TRUE.toString());
                                    profileFieldValues.add(pfValue);
                                }
                            }
                        }
                        profileField.setValues(profileFieldValues); ///
                    }
                    profileFields.add(profileField);
                }
            }
        } catch (DAOException e) {
            LOGGER.error(e.getCause());
        } finally {
            daoFactory.putConnection(connection);
        }
        result.setFields(profileFields);
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
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        result.add(new FieldValue(profileField.getId(), applicationId, null,
                                null, format.parse(profileField.getValues().get(0).getValue()), null));
                    } catch (ParseException e) {
                        LOGGER.warn(e.getCause());
                    }
                    break;
                case "select":
                case "checkbox":
                case "radio":
                    for (ProfileFieldValue profileFieldValue : profileField.getValues()) {
                        if (Boolean.parseBoolean(profileFieldValue.getValue())) {
                            result.add(new FieldValue(profileField.getId(), applicationId, null,
                                    null, null, Integer.parseInt(profileFieldValue.getId())));
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
        Application resultSet = null;
        try {
            resultSet = applicationDAO.getApplicationByUserCES(userId, cesId);
        } catch (DAOException e) {
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
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
            } catch (SQLException exp) {
                throw new DAOException(exp);
            }
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
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
            } catch (SQLException exp) {
                throw new DAOException(exp);
            }
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void setProfile(int userId, Profile profile) throws DAOException {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            CES ces = cesDAO.getCurrentCES();
            if (isApplied(userId, ces.getId())) {
                updateProfile(profile, userId, ces.getId());
            } else {
                createProfile(profile, userId, ces.getId());
            }
        } catch (DAOException e) {
            LOGGER.error(e.getCause());
            throw new DAOException(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }
}
