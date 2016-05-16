package ua.nc.service;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Interviewer;
import ua.nc.entity.profile.StudentData;

import java.util.List;

/**
 * Created by creed on 06.05.16.
 */
public interface InterviewerService {
    public List<Interviewer> getInterviewer(Integer itemPerPage, Integer pageNumber);

//    public StudentData getInterviewer(Integer itemPerPage, Integer pageNumber, Integer orderBy);

//    public Student findInterviewerByName(String name, String surname);

//    public List<Student> findInterviewerByUniversity(String university);

    public void changeStatus(String action, List<Integer> studentsId);

    public void activateInterviewer(List<Integer> studentsId);

    public void deactivateInterviewer(List<Integer> studentsId);

    public void rejectInterviewer(List<Integer> studentsId);

    public Integer getInterviewerSize();


}
