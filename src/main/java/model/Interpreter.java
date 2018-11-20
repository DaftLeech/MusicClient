package model;

public class Interpreter {

    private long interID;
    private String interName;

    public Interpreter(){

    }

    public Interpreter(String interName){
        this.interName = interName;
    }

    public String getInterName(){
        return interName;
    }

    public long getInterID() {
        return interID;
    }
}
