package ua.nc.service;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public interface StudentService {

//    public List<Student> getStudents(Integer id, Integer currentCESId);

//    public Student findStudentByName(String name, String surname);

//    public List<Student> findStudentsByUniversity(String university);

    public void changeStatus(String action, List<Integer> studentsId);

    public void activateStudents(List<Integer> studentsId);

    public void deactivateStudents(List<Integer> studentsId);

    public void rejectStudents(List<Integer> studentsId);

}
