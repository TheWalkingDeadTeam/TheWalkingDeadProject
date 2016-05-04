package ua.nc.entity.ces;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Ермоленко on 01.05.2016.
 */
public class CES {
    private Integer id;
    private Integer year;
    private Calendar startRegistrationDate;
    private Calendar endRegistrationDate;
    private Calendar startInterviewingDate;
    private Calendar endInterviewingDate;
    private Integer quota;
    private Integer reminders;
    private List<Object> status;
    private Integer interviewTimeForPerson;
    private Integer interviewTimeForDay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Calendar getStartRegistrationDate() {
        return startRegistrationDate;
    }

    public void setStartRegistrationDate(Calendar startRegistrationDate) {
        this.startRegistrationDate = startRegistrationDate;
    }

    public Calendar getEndRegistrationDate() {
        return endRegistrationDate;
    }

    public void setEndRegistrationDate(Calendar endRegistrationDate) {
        this.endRegistrationDate = endRegistrationDate;
    }

    public Calendar getStartInterviewingDate() {
        return startInterviewingDate;
    }

    public void setStartInterviewingDate(Calendar startInterviewingDate) {
        this.startInterviewingDate = startInterviewingDate;
    }

    public Calendar getEndInterviewingDate() {
        return endInterviewingDate;
    }

    public void setEndInterviewingDate(Calendar endInterviewingDate) {
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

    public CESStatus getStatus() {
        CESStatus statusObj = new CESStatus((Integer) status.get(0), (String) status.get(1));
        return statusObj;
    }

    public void setStatus(CESStatus statusObj) {
        status.set(0, statusObj.getId());
        status.set(1, statusObj.getName());
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
}

