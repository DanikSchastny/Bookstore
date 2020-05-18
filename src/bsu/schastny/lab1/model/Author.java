package bsu.schastny.lab1.model;

import java.util.List;

public class Author {
    private int id;
    private List<Book> books;
    private String authorName;

    public Author(int id, List<Book> books){
        this.id = id;
        this.books = books;
    }

    public Author(int id, List<Book> books, String authorName){
        this.id = id;
        this.books = books;
    }

    public Author(int id, String authorName){
        this.id = id;
        this.authorName = authorName;
    }

    public Author(){}

    public String getAuthorName(){
        return this.authorName;
    }

    public List<Book> getBooks(){
        return this.books;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
