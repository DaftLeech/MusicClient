package model;

public class User {

    private long userID;
    private long sessionID;

    public User(){

    }
    public User(long userID, long sessionID){
        this.userID = userID;
        this.sessionID = sessionID;
    }

    public long getUserID() {
        return userID;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }
}
