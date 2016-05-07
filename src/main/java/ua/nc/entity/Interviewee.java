package ua.nc.entity;

import java.util.Date;

/**
 * Created by Rangar on 02.05.2016.
 */
public class Interviewee implements Identified<Integer> {
    private int applicationID;
    private Date interviewTime;
    private String specialMark;
    private Integer devFeedbackID;
    private Integer hrFeedbackID;

    public Interviewee(int id) {
        this.applicationID = id;
    }
    public Interviewee(){}

    @Override
    public Integer getId() {
        return applicationID;
    }

    protected void setId(int id) {
        this.applicationID = id;
    }

    public Date getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(Date interview_time) {
        this.interviewTime = interview_time;
    }

    public Integer getHrFeedbackID() {
        return hrFeedbackID;
    }

    public void setHrFeedbackID(Integer hrFeedbackID) {
        this.hrFeedbackID = hrFeedbackID;
    }

    public String getSpecialMark() {
        return specialMark;
    }

    public void setSpecialMark(String special_mark) {
        this.specialMark = special_mark;
    }

    public Integer getDevFeedbackID() {
        return devFeedbackID;
    }

    public void setDevFeedbackID(Integer devFeedbackID) {
        this.devFeedbackID = devFeedbackID;
    }
}
