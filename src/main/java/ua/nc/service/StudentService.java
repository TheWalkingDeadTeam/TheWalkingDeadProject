package ua.nc.service;

import ua.nc.entity.profile.StudentData;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public interface StudentService {
    StudentData getStudents(Integer itemPerPage, Integer pageNumber);

    StudentData getStudents(Integer itemPerPage, Integer pageNumber, String pattern);

    StudentData getStudents(Integer itemPerPage, Integer pageNumber, Integer orderBy, Boolean asc);

    Integer getSize(String pattern);

    void changeStatus(String action, List<Integer> studentsId);


}
