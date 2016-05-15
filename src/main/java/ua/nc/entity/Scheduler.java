package ua.nc.entity;

/**
 * Created by Alexander on 08.05.2016.
 */
public class Scheduler {
    private Integer mailIdUser;
    private String locations;
    private Integer mailIdStaff;
    private String contactStaff;
    private String courseType;
    private String interviewTime;

    public String getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
    }

    @Override
    public String toString() {
        return "Scheduler{" +
                "mailIdUser=" + mailIdUser +
                ", locations='" + locations + '\'' +
                ", mailIdStaff=" + mailIdStaff +
                ", contactStaff='" + contactStaff + '\'' +
                ", courseType='" + courseType + '\'' +
                ", interviewTime='" + interviewTime + '\'' +
                ", contactStudent='" + contactStudent + '\'' +
                '}';
    }

    public String getContactStudent() {
        return contactStudent;
    }

    public void setContactStudent(String contactStudent) {
        this.contactStudent = contactStudent;
    }

    public String getContactStaff() {
        return contactStaff;
    }

    public void setContactStaff(String contactStaff) {
        this.contactStaff = contactStaff;
    }

    private String contactStudent;


    public Integer getMailIdUser() {
        return mailIdUser;
    }

    public void setMailIdUser(Integer mailIdUser) {
        this.mailIdUser = mailIdUser;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public Integer getMailIdStaff() {
        return mailIdStaff;
    }

    public void setMailIdStaff(Integer mailIdStaff) {
        this.mailIdStaff = mailIdStaff;
    }


    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}