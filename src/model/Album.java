package model;

import java.util.ArrayList;

public class Album {

    private String name;
    private Interpreter interpreter;
    private ArrayList<Song> songs;

    public Album(String name, Interpreter interpreter, ArrayList<Song> songs){
        this.name = name;
        this.interpreter = interpreter;
        this.songs = songs;
    }

    public String getName(){
        return name;
    }

    public Interpreter getInterpreter(){
        return interpreter;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
