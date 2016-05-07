package ua.nc.service;

import ua.nc.entity.profile.StudentData;

import java.util.List;

/**
 * Created by Rangar on 07.05.2016.
 */
public interface StudentDataService {
    List<StudentData> getStudentsData(Integer itemsPerPage, Integer itemNumber);

    List<StudentData> findStudentDataByName(String name, String surname);

    List<StudentData> findStudentDataByUniversity(String university);

    void rejectStudents(List<Integer> studentsId);
}
