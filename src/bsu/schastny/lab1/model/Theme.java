package bsu.schastny.lab1.model;

public class Theme {
    private int id;
    private String name;

    public Theme(){}

    public Theme(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setName(final String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setId(int id){
        this.id = id;
    }
}
