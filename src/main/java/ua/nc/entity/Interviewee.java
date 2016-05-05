package ua.nc.entity;

import java.util.Date;

/**
 * Created by Rangar on 02.05.2016.
 */
public class Interviewee implements Identified<Integer> {
    private int applicationID;
    private Date interview_time;
    private String special_mark;
    private Integer devFeedbackID;
    private Integer hrFeedbackID;

    @Override
    public Integer getId() {
        return applicationID;
    }

    protected void setId(int id){
        this.applicationID = id;
    }

    public Date getInterview_time() {
        return interview_time;
    }

    public void setInterview_time(Date interview_time) {
        this.interview_time = interview_time;
    }

    public Integer getHrFeedbackID() {
        return hrFeedbackID;
    }

    public void setHrFeedbackID(Integer hrFeedbackID) {
        this.hrFeedbackID = hrFeedbackID;
    }

    public String getSpecial_mark() {
        return special_mark;
    }

    public void setSpecial_mark(String special_mark) {
        this.special_mark = special_mark;
    }

    public Integer getDevFeedbackID() {
        return devFeedbackID;
    }

    public void setDevFeedbackID(Integer devFeedbackID) {
        this.devFeedbackID = devFeedbackID;
    }

    public Interviewee(int id){
        this.applicationID = id;
    }
}
