package ua.nc.entity;

/**
 * Created by Rangar on 02.05.2016.
 */
public class Feedback implements Identified<Integer> {
    private int id;
    private int score;
    private String comment;
    private int interviewerID;

    @Override
    public Integer getId() {
        return id;
    }

    protected void setId(int id){
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getInterviewerID() {
        return interviewerID;
    }

    public void setInterviewerID(int interviewerID) {
        this.interviewerID = interviewerID;
    }

    public Feedback(int score, String comment, int interviewerID){
        this.score = score;
        this.comment = comment;
        this.interviewerID = interviewerID;
    }
}
