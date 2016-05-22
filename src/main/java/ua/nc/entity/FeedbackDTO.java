package ua.nc.entity;

import ua.nc.dao.enums.UserRoles;

/**
 * Created by Hlib on 22.05.2016.
 */
public class FeedbackDTO {

    private final String RESTRICTED = "restricted";
    private final String INTERVIEWING_PERIOD = "interviewing period";
    private final String AFTER_INTERVIEWING_PERIOD = "after interviewing period";

    UserRoles interviewerRole;
    String viewLevel;
    boolean applicationExists;
    boolean intervieweeExists;
    boolean restricted;
    Feedback devFeedback;
    Feedback hrFeedback;
    String specialMark;

    public UserRoles getInterviewerRole() {
        return interviewerRole;
    }

    public void setInterviewerRole(UserRoles interviewerRole) {
        this.interviewerRole = interviewerRole;
    }

    public void setRestricted(){
        viewLevel = RESTRICTED;
    }

    public void setInterviewingPeriod(){
        viewLevel = INTERVIEWING_PERIOD;
    }

    public void setAfterInterviewingPeriod(){
        viewLevel = AFTER_INTERVIEWING_PERIOD;
    }

    public boolean isApplicationExists() {
        return applicationExists;
    }

    public void setApplicationExists(boolean applicationExists) {
        this.applicationExists = applicationExists;
    }

    public boolean isIntervieweeExists() {
        return intervieweeExists;
    }

    public void setIntervieweeExists(boolean intervieweeExists) {
        this.intervieweeExists = intervieweeExists;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public Feedback getDevFeedback() {
        return devFeedback;
    }

    public void setDevFeedback(Feedback devFeedback) {
        this.devFeedback = devFeedback;
    }

    public Feedback getHrFeedback() {
        return hrFeedback;
    }

    public void setHrFeedback(Feedback hrFeedback) {
        this.hrFeedback = hrFeedback;
    }

    public String getSpecialMark() {
        return specialMark;
    }

    public void setSpecialMark(String specialMark) {
        this.specialMark = specialMark;
    }
}
