package ua.nc.service;

import ua.nc.entity.Interviewee;
import ua.nc.entity.IntervieweeRow;

import java.util.List;

/**
 * Created by Hlib on 10.05.2016.
 */
public interface IntervieweeService {
    Interviewee getInterviewee(int id);

    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber);

    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy);

    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy, Boolean asc);

    public List<IntervieweeRow> getInterviewee(Integer itemPerPage, Integer pageNumber, String orderBy, String pattern);

    public void changeStatus(String action, List<Integer> studentsId);

    public void subscribeInterviewee(List<Integer> studentsId);

    public void unsubscribeInterviewee(List<Integer> studentsId);

    public Integer getIntervieweeSize(String pattern);
}
