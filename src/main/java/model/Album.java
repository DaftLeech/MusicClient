package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Album {

    private long albumID;
    private String albumName;
    private long interID;
    private Interpreter interpreter;
    private ArrayList<Song> songs;

    public Album(){

    }

    public Album(long albumID, String albumName, long interID){

    }

    @JsonIgnore
    public Album(long albumID,String albumName, Interpreter interpreter, ArrayList<Song> songs){
        this.albumID = albumID;
        this.albumName = albumName;
        this.interpreter = interpreter;
        this.songs = songs;
    }

    public String getAlbumName(){
        return albumName;
    }

    @JsonIgnore
    public Interpreter getInterpreter(){
        return interpreter;
    }

    @JsonIgnore
    public ArrayList<Song> getSongs() {
        return songs;
    }

    public long getAlbumID() {
        return albumID;
    }

    public long getInterID() {
        return interID;
    }

    public void setInterpreter(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
