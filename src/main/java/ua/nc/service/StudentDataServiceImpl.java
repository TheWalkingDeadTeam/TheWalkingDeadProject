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
