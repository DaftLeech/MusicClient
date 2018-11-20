package model;

public class Song {

    private long songID;
    private String songName;
    private long songLength;
    private long albumID;

    public Song(){

    }

    public Song(long songID, String songName, long songLength, long albumID){
        this.songName = songName;
        this.songID = songID;
        this.songLength = songLength;
        this.albumID = albumID;
    }

    public String getSongName(){
        return songName;
    }

    public long getSongLength(){
        return songLength;
    }

    public long getSongID() {
        return songID;
    }

    public long getAlbumID() {
        return albumID;
    }
}
