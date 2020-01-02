package pl.sanders;

public class ScoreboardElement {
    protected String name=" ";
    protected int gainedTime=-1;

    public ScoreboardElement(String name, int time){
        this.name=name;
        this.gainedTime=time;
    }

    @Override
    public String toString() {

        return name + "\t " + gainedTime;
    }
}
