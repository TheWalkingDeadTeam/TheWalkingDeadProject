package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreApplicationTableDAO;
import ua.nc.entity.CES;
import ua.nc.entity.profile.StudentData;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;


/**
 * Created by creed on 06.05.16.
 */
public class StudentServiceImpl implements StudentService {
    private final static Logger log = Logger.getLogger(StudentServiceImpl.class);
    private final DAOFactory daoFactory = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);

    @Override
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, Integer orderBy) {
        Connection connection = daoFactory.getConnection();
        PostgreApplicationTableDAO applicationTableDAO = new PostgreApplicationTableDAO(connection);
        CESServiceImpl cesService = new CESServiceImpl();
        CES ces = cesService.getCurrentCES();
        try {
            //ORDER BY
            return applicationTableDAO.getApplicationsTable(ces.getId(),itemPerPage,pageNumber);
        } catch (DAOException e) {
            log.warn("Can't get students", e.getCause());
        }
        return null;
    }

    /**
     * @param action
     * @param studentsId list of Integer
     */
    @Override
    public void changeStatus(String action, List<Integer> studentsId) {
        if (Objects.equals(action, "activate")) {
            activateStudents(studentsId);
            System.out.println("activate");
        } else if (Objects.equals(action, "deactivate")) {
            deactivateStudents(studentsId);
            System.out.println("deactivate");
        } else if (Objects.equals(action, "reject")) {
            rejectStudents(studentsId);
            System.out.println("reject");
        } else {
            log.error(action + " action not supported");
        }
    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void activateStudents(List<Integer> studentsId) {
        // StudentListDAO
        //метод, который активирует список студентов
    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void deactivateStudents(List<Integer> studentsId) {
        // StudentListDAO
        //метод, которые деактивирует список студентов
    }

    /**
     * @param studentsId list of Integer
     */
    @Override
    public void rejectStudents(List<Integer> studentsId) {
        // StudentListDAO
        // метод, который обидит список студентов
    }
}
