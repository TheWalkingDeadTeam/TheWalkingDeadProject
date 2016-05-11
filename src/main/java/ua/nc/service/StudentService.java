package ua.nc.service;

import ua.nc.entity.profile.StudentData;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public interface StudentService {
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber);

    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, Integer orderBy);

//    public Student findStudentByName(String name, String surname);

//    public List<Student> findStudentsByUniversity(String university);

    public void changeStatus(String action, List<Integer> studentsId);

    public void activateStudents(List<Integer> studentsId);

    public void deactivateStudents(List<Integer> studentsId);

    public void rejectStudents(List<Integer> studentsId);

    public Integer getStudentsSize();
}