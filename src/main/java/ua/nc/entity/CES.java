package ua.nc.entity;

import java.util.Date;
import java.util.List;

/**
 * Created by Ермоленко on 01.05.2016.
 */
public class CES implements Identified<Integer>{
    private Integer id;
    private Integer year;
    private Date startRegistrationDate;
    private Date endRegistrationDate;
    private Date startInterviewingDate;
    private Date endInterviewingDate;
    private Integer quota;
    private Integer reminders;
    private Integer statusId;
    private Integer interviewTimeForPerson;
    private Integer interviewTimeForDay;

    public CES(Integer year, Date startRegistrationDate, Date endRegistrationDate, Integer quota, Integer reminders, Integer statusId,
               Integer interviewTimeForPerson, Integer interviewTimeForDay){
        this.year = year;
        this.startRegistrationDate = startRegistrationDate;
        this.endRegistrationDate = endRegistrationDate;
        this.quota = quota;
        this.reminders = reminders;
        this.statusId = statusId;
        this.interviewTimeForPerson = interviewTimeForPerson;
        this.interviewTimeForDay = interviewTimeForDay;
    }
    public CES(){}

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getStartRegistrationDate() {
        return startRegistrationDate;
    }

    public void setStartRegistrationDate(Date startRegistrationDate) {
        this.startRegistrationDate = startRegistrationDate;
    }

    public Date getEndRegistrationDate() {
        return endRegistrationDate;
    }

    public void setEndRegistrationDate(Date endRegistrationDate) {
        this.endRegistrationDate = endRegistrationDate;
    }

    public Date getStartInterviewingDate() {
        return startInterviewingDate;
    }

    public void setStartInterviewingDate(Date startInterviewingDate) {
        this.startInterviewingDate = startInterviewingDate;
    }

    public Date getEndInterviewingDate() {
        return endInterviewingDate;
    }

    public void setEndInterviewingDate(Date endInterviewingDate) {
        this.endInterviewingDate = endInterviewingDate;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getReminders() {
        return reminders;
    }

    public void setReminders(Integer reminders) {
        this.reminders = reminders;
    }

    public Integer getInterviewTimeForPerson() {
        return interviewTimeForPerson;
    }

    public void setInterviewTimeForPerson(Integer interviewTimeForPerson) {
        this.interviewTimeForPerson = interviewTimeForPerson;
    }

    public Integer getInterviewTimeForDay() {
        return interviewTimeForDay;
    }

    public void setInterviewTimeForDay(Integer interviewTimeForDay) {
        this.interviewTimeForDay = interviewTimeForDay;
    }

    @Override
    public Integer getId() {
        return id;
    }

    protected void setId(int id){
        this.id = id;
    }
}

