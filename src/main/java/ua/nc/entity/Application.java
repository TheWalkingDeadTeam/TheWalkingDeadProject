package ua.nc.entity;

/**
 * Created by Rangar on 01.05.2016.
 */
public class Application implements Identified<Integer> {
    private int ID;
    private int userID;
    private int cesID;
    private Boolean rejected;

    @Override
    public Integer getID() {
        return null;
    }

    public int getCesID() {
        return cesID;
    }

    public void setCesID(int cesID) {
        this.cesID = cesID;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Application(int userID, int cesID){
        this.userID = userID;
        this.cesID = cesID;
    }
}
