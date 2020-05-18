package bsu.schastny.lab1.model;

import java.util.List;

public class Book {
    private int id;
    private String authorName;
    private String name;
    private List<Theme> themes;

    public Book(){}

    public Book(final int id,  String authorName, final String name, final List<Theme> themes){
        this.id = id;
        this.authorName = authorName;
        this.name = name;
        this.themes = themes;
    }

    public int getId(){
        return this.id;
    }

    public String getAuthorName(){
        return this.authorName;
    }

    public String getName(){
        return this.name;
    }

    public List<Theme> getThemes(){
        return themes;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setThemes(List<Theme> themes){
        this.themes = themes;
    }
}
