package ua.nc.service;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public class StudentServiceImpl implements StudentService{
    private final static Logger log = Logger.getLogger(StudentServiceImpl.class);


    /**
     *
     * @param action
     * @param studentsId list of Integer
     */
    @Override
    public void changeStatus(String action, List<Integer> studentsId) {
        if (action == "activate"){
            activateStudents(studentsId);
        }
        else if (action == "deactivate") {
            deactivateStudents(studentsId);
        }
        else if (action == "reject") {
            rejectStudents(studentsId);
        }
        else {
            log.error(action + " action not supported");
        }
    }

    /**
     *
     * @param studentsId list of Integer
     */
    @Override
    public void activateStudents(List<Integer> studentsId) {
        // StudentListDAO
        //метод, который активирует список студентов
    }

    /**
     *
     * @param studentsId list of Integer
     */
    @Override
    public void deactivateStudents(List<Integer> studentsId) {
        // StudentListDAO
        //метод, которые деактивирует список студентов
    }

    /**
     *
     * @param studentsId list of Integer
     */
    @Override
    public void rejectStudents(List<Integer> studentsId) {
        // StudentListDAO
        // метод, который обидит список студентов
    }
}
