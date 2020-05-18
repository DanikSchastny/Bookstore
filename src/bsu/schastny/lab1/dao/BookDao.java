package bsu.schastny.lab1.dao;

import bsu.schastny.lab1.ConsoleService;
import bsu.schastny.lab1.model.Author;
import bsu.schastny.lab1.model.Book;
import bsu.schastny.lab1.model.Theme;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;
import java.util.*;

import static bsu.schastny.constants.ApplicationStringConstants.*;

public class BookDao {
    private Connection connection;
    private Statement statement;

    private Set<Author> authors;
    private Set<Book> books;
    private Set<Theme> themes;

    public void updateName(String newName, String bookName){
        try{
            String sql = "UPDATE books SET book_name=(?) WHERE book_name=(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2,bookName);
            preparedStatement.setString(1,newName);
            preparedStatement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void updateAuthor(String bookname, int id){
        try {
            String sql = "UPDATE books SET author_id=(?) WHERE book_name=(?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2, bookname);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initNewAuthor(Author author, Book book, Theme theme, ResultSet resultSet, List<Book> authorBooks, List<Theme> booksThemes) throws SQLException {
        author.setAuthorName(resultSet.getString(AUTHOR_NAME));
        author.setId(resultSet.getInt(AUTHOR_ID));

        book.setName(resultSet.getString(BOOK_NAME));

        book.setId(resultSet.getInt(BOOK_ID));
        book.setAuthorName(resultSet.getString(AUTHOR_NAME));

        theme.setName(resultSet.getString(THEME_NAME));
        theme.setId(resultSet.getInt(THEME_ID));
        booksThemes.add(theme);
        themes.add(theme);
    }

    public void addTheme(String themename){
        try {
            String sql = "INSERT INTO themes (theme_name) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, themename);
            preparedStatement.executeQuery();
        }
        catch (Exception e){

        }
    }
    private void fillData(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        List<Book> authorsBooks = new ArrayList<>();
        Book book = new Book();
        List<Theme> booksThemes = new ArrayList<>();
        Theme theme = new Theme();
        authors = new HashSet<>();
        books = new HashSet<>();
        themes = new HashSet<>();


        resultSet.next();

        initNewAuthor(author, book, theme, resultSet, authorsBooks, booksThemes);

        while (resultSet.next()) {
            if (author.getAuthorName().equals(resultSet.getString(AUTHOR_NAME))) {
                theme = new Theme(resultSet.getInt(THEME_ID), resultSet.getString(THEME_NAME));
                themes.add(theme);
                if (book.getName().equals(resultSet.getString(BOOK_NAME))) {
                    booksThemes.add(theme);
                } else {
                    book.setThemes(booksThemes);
                    books.add(book);
                    authorsBooks.add(book);
                    book = new Book();
                    book.setId(resultSet.getInt(BOOK_ID));
                    book.setName(resultSet.getString(BOOK_NAME));
                    book.setAuthorName(resultSet.getString(AUTHOR_NAME));
                    booksThemes = new ArrayList<>();
                    booksThemes.add(theme);
                }
            } else {
                book.setThemes(booksThemes);
                authorsBooks.add(book);
                books.add(book);
                book = new Book();
                booksThemes = new ArrayList<>();
                author.setBooks(authorsBooks);
                authors.add(author);
                authorsBooks = new ArrayList<>();
                author = new Author();
                theme = new Theme();

                initNewAuthor(author, book, theme, resultSet, authorsBooks, booksThemes);
            }
        }

        book.setThemes(booksThemes);
        books.add(book);
        authorsBooks.add(book);
        authors.add(author);

    }

    public void readData() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT author_name, book_name,theme_name,authors.author_id,books.book_id, themes.theme_id FROM authors NATURAL JOIN(books JOIN book_theme ON books.book_id = book_theme.book_ID JOIN themes ON book_theme.theme_ID = themes.theme_id) ORDER BY authors.author_id, author_name, books.book_id, book_name, themes.theme_id, theme_name"
        );
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        fillData(resultSet);

        ConsoleService consoleService = new ConsoleService();
        consoleService.init(books);
        consoleService.print(books);
    }

    private void init(Connection connection, Statement statement) throws SQLException {
        String[] initStatements = {
                "CREATE DATABASE if not exists librarydatabase",
                "use librarydatabase",
        };
        for (String st : initStatements) {
            statement.executeUpdate(st);
        }
    }

    public BookDao() throws Exception {
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        statement = connection.createStatement();
        init(connection, statement);
    }

    public void addNewAuthor(String authorName) {
        try {

            String sql = "INSERT INTO authors (author_name) VALUES (?)";

            PreparedStatement insertAuthor = connection.prepareStatement(sql);
            insertAuthor.setString(1, authorName);
            insertAuthor.executeUpdate();
        } catch (MySQLIntegrityConstraintViolationException e) {
            System.out.println("Таблица уже содержит этого автора");
        } catch (SQLException e) {
            //System.out.println("Таблица уже содержит этого автора");
        }

    }

    public List<Author> getAuthorList() {
        String sql = "SELECT * FROM authors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql
            );

            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            List<Author> authors = new ArrayList<>();

            while (resultSet.next()) {
                String authorname = resultSet.getString("author_name");
                int authorId = Integer.parseInt(resultSet.getString("author_id"));
                Author author = new Author(authorId, authorname);
                authors.add(author);
            }

            return authors;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Theme> getThemeList() {
        String sql = "SELECT * FROM themes";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql
            );
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            List<Theme> themes = new ArrayList<>();

            while(resultSet.next()){
                Theme theme = new Theme( Integer.parseInt(resultSet.getString("theme_id")),  resultSet.getString("theme_name"));
                themes.add(theme);
            }

            return themes;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }


    public boolean insertBook(Book book, List<Integer> themes, int authorId){

        try {

            String insert = "INSERT INTO books (book_name, author_id) VALUES (?,?)";
            PreparedStatement insertBook = connection.prepareStatement(insert);
            insertBook.setString(1, book.getName());
            insertBook.setInt(2, authorId);

            System.out.println(authorId);
            insertBook.executeUpdate();

            String bookName = book.getName();
            String select = "SELECT book_id from books where book_name =(?)";
            PreparedStatement getBookId = connection.prepareStatement(select);
            getBookId.setString(1, book.getName());
            getBookId.executeQuery();

            int bookId = 0;
            while(getBookId.getResultSet().next()) {
                bookId = getBookId.getResultSet().getInt("book_id");
            }

            String nocheck = "SET foreign_key_checks=0";
            PreparedStatement ps = connection.prepareStatement(nocheck);
            ps.executeUpdate();

            String insertRef = "INSERT INTO book_theme(book_ID, theme_ID) VALUES (?,?)";
            for(int i = 0; i < themes.size(); ++i){
                PreparedStatement insertReference = connection.prepareStatement(insertRef);
                insertReference.setInt(1, bookId);
                insertReference.setInt(2, themes.get(i));
                insertReference.executeUpdate();

            }

            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public void deleteBook(String bookname){
        try{
            String sql ="DELETE FROM books WHERE book_NAME =(?)";
            PreparedStatement deleteBook = connection.prepareStatement(sql);
            deleteBook.setString(1, bookname);
            deleteBook.executeUpdate();
        }
        catch (SQLException e){

        }

    }
    public List<String> getBookTitles(){
        try {
            String sql = "SELECT book_name from books";
            PreparedStatement insertReference = connection.prepareStatement(sql);
            insertReference.executeQuery();

            List<String> bookNames = new ArrayList<>();
            while(insertReference.getResultSet().next()){
                bookNames.add(insertReference.getResultSet().getString("book_name"));

            }

            return bookNames;
        }
        catch (Exception e){

        }
        return null;
    }

    public List<String> getThemes(String bookname){
        try{
            String sql = " select theme_name from themes left join book_theme on themes.theme_id = book_theme.theme_ID left join books on books.book_id = book_theme.book_ID where books.book_name = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

        }
        catch (Exception e){


        }
        return null;
    }


    public List<String> getBookNames(String theme){
        try {
            String sql = " SELECT book_name from books left join book_theme on books.book_id = book_theme.book_ID left join themes on themes.theme_id = book_theme.theme_ID where theme_name=(?) group by  books.book_name";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, theme);
            ps.executeQuery();

            List<String> books = new ArrayList<>();
            while(ps.getResultSet().next()){
                books.add(ps.getResultSet().getString("book_name"));
            }

            return books;

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void addThemeWithBook(String bookname, String titleName) {
        try {
            String sql = "INSERT INTO themes (theme_name) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookname);
            preparedStatement.executeUpdate();

            String nocheck = "SET foreign_key_checks=0";
            PreparedStatement ps = connection.prepareStatement(nocheck);
            ps.executeUpdate();

            String sql1 = "INSERT INTO book_theme (book_ID, theme_ID) values (?,?)";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement1.setInt(1, getBookId(bookname));
            preparedStatement1.setInt(2, getThemeId(titleName));

            preparedStatement1.executeUpdate();


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getThemeId(String theme){
        try{
            String sql = "SELECT theme_id FROM themes WHERE theme_name =(?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,theme);
            preparedStatement.executeQuery();

            while(preparedStatement.getResultSet().next()){
                return preparedStatement.getResultSet().getInt("theme_id");
            }
        }
         catch (Exception e){

         }
        return 0;
    }
    public int getBookId(String bookname){
        try {
            String sql = "SELECT book_id FROM books WHERE book_name = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookname);
            preparedStatement.executeQuery();

            int id = 0;
            while (preparedStatement.getResultSet().next()) {
                id = preparedStatement.getResultSet().getInt("book_id");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
