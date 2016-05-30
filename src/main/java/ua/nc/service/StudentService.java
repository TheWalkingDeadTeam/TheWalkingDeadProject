package ua.nc.service;

import ua.nc.entity.profile.StudentData;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public interface StudentService {
    public StudentData getStudents(Integer itemPerPage, Integer pageNumber);

    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, String pattern);

    public StudentData getStudents(Integer itemPerPage, Integer pageNumber, Integer orderBy, Boolean asc);

    public Integer getSize(String pattern);

    public void changeStatus(String action, List<Integer> studentsId);


}
