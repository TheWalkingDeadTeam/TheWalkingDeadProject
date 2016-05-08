package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.ApplicationDAO;
import ua.nc.dao.CESDAO;
import ua.nc.dao.UserDAO;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.entity.Application;
import ua.nc.entity.User;
import ua.nc.entity.profile.Profile;
import ua.nc.entity.profile.ProfileField;
import ua.nc.entity.profile.ProfileFieldValue;
import ua.nc.entity.profile.StudentData;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

/**
 * Created by Rangar on 07.05.2016.
 */
public class StudentDataServiceImpl implements StudentDataService {
    private final static Logger log = Logger.getLogger(StudentDataServiceImpl.class);
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public List<StudentData> getStudentsData(Integer itemsPerPage, Integer itemNumber) {
        return null;
    }

    // it's experimental shit, no panic
    private StudentData getStudentData(Integer userId){
        Connection connection = daoFactory.getConnection();
        UserDAO userDAO = daoFactory.getUserDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        ProfileService profileService = new ProfileServiceImpl();
        StudentData result = new StudentData();
        try {
            int currentCESId = cesDAO.getCurrentCES().getId();
            User user = null; // = userDAO.read(userId); that DAO has pathetic methods :D
            result.setId(user.getId());
            result.setName(user.getName()); // only name here, maybe i'm missing some User.java updates
            Profile profile = profileService.getProfile(user.getId(), currentCESId);
            List<ProfileField> profileFields = profile.getFields();
            for (ProfileField profileField : profile.getFields()){
                if (profileField.getFieldType().equals("textarea") || profileField.getFieldType().equals("tel")){
                    profileFields.remove(profileField);
                }
                if (profileField.getFieldType().equals("select") || profileField.getFieldType().equals("checkbox")
                        || profileField.getFieldType().equals("radio")){
                    List<ProfileFieldValue> profileFieldValues = profileField.getValues();
                    for (ProfileFieldValue profileFieldValue : profileField.getValues()){
                        if (!Boolean.parseBoolean(profileFieldValue.getValue())){
                            profileFieldValues.remove(profileFieldValue);
                        }
                    }
                    profileField.setValues(profileFieldValues);
                }
            }
            profile.setFields(profileFields);
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            daoFactory.putConnection(connection);
        }
        return result;
    }

    @Override
    public List<StudentData> findStudentDataByName(String name, String surname) {
        return null;
    }

    @Override
    public List<StudentData> findStudentDataByUniversity(String university) {
        return null;
    }

    @Override
    public void rejectStudents(List<Integer> studentsId) {
        Connection connection = daoFactory.getConnection();
        ApplicationDAO applicationDAO = daoFactory.getApplicationDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            List<Application> applications = applicationDAO.getAllCESApplications(cesDAO.getCurrentCES().getId());
            for (Application application : applications){
                application.setRejected(true);
                applicationDAO.update(application);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            daoFactory.putConnection(connection);
        }
    }
}
