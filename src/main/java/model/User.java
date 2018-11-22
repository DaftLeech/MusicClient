package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    private long userID;
    private long sessionID;
    private String userName;

    public User(){

    }
    public User(long userID, long sessionID){
        this.userID = userID;
        this.sessionID = sessionID;
    }

    @JsonIgnore
    public String getUserName(){
        return userName;
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

    @JsonIgnore
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
