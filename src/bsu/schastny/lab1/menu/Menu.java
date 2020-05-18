package bsu.schastny.lab1.menu;

import bsu.schastny.lab1.dao.BookDao;
import bsu.schastny.lab1.model.Author;
import bsu.schastny.lab1.model.Book;
import bsu.schastny.lab1.model.Theme;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static bsu.schastny.constants.ApplicationStringConstants.*;

public class Menu {
    private String enteringLine;
    private Scanner scanner;
    private BookDao bookDao;

    public Menu() throws Exception {
        enteringLine = new String();
        scanner = new Scanner(System.in);
        bookDao = new BookDao();
    }

    public void deleteTheme(String bookname){

    }


    public void changeThemes(String bookname){
        System.out.println("1. Добавить новую тему");
        System.out.println("2. Удалить существующую");

        int res = Integer.parseInt(scanner.nextLine());
            switch (res) {
                case 1:
                    addNewTheme(bookname);
                    break;

                case 2:
                    deleteTheme(bookname);
                    break;
            }


        }

    public void changeAuthor(String bookname) {
        System.out.println("1. Выбрать из уже существующих авторов");
        System.out.println("2. Создать нового");

        int res = Integer.parseInt(scanner.nextLine());

        switch (res) {
            case 1: {
                System.out.println("Выберите id автора: ");
                for (Author author : bookDao.getAuthorList()) {
                    System.out.println(author.getId() + ". " + author.getAuthorName());
                }
                res = Integer.parseInt(scanner.nextLine());

                bookDao.updateAuthor(bookname, res);

            }

        }

    }

    public void changeName(String bookname) {
        System.out.println("Введите новое имя книги");
        String newBoookName=scanner.nextLine();
        bookDao.updateName( newBoookName,  bookname);

    }

    public void addNewTheme(String bookname){
        System.out.println("Введите название");
        String titleName = scanner.nextLine();

        bookDao.addThemeWithBook(bookname,titleName );
    }

    public void editThemes(String bookname){
        System.out.println("1. Создать новую тему");
        System.out.println("2. Выбрать из уже существующих");

        int res = Integer.parseInt(scanner.nextLine());
        switch (res){
            case 1:{
                addNewTheme(bookname);
            }
        }
    }

    public void editBook() {
        boolean flag = true;
        String bookname = "";
        while (true) {
            System.out.println("Выберите книгу, которую хотите изменить");
            int i = 1;
            for (String s : bookDao.getBookTitles()) {
                System.out.println(i++ + ". " + s);
            }


            try {
                bookname = bookDao.getBookTitles().get(Integer.parseInt(scanner.nextLine()) - 1);
            }
            catch (Exception e){

            }
            break;
        }

        while (flag) {
            System.out.println("Выберите, что вы хотите изменить в книге: ");
            System.out.println("1. Автор");
            System.out.println("2. Название");
            System.out.println("3. Темы");
            System.out.println("4. Выйти");


            int res = Integer.parseInt(scanner.nextLine());

            switch (res) {
                case 0:
                    flag = false;
                    break;


                case 1:
                    changeAuthor(bookname);
                    break;

                case 2:
                    changeName(bookname);
                    flag = false;
                    break;

                case 3:
                    changeThemes(bookname);
                    break;

            }

        }


    }


    public void display() {
        String enteringline = new String();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(ANSI_CYAN + MENU_CHOOSE_OPERATION + ANSI_RESET);
            System.out.println(ANSI_GREEN + MENU_SHOW);
            System.out.println(MENU_ADD);
            System.out.println(MENU_DELETE);
            System.out.println(MENU_EDIT);
            System.out.println(MENU_SEARCH_BY_THEME);
            System.out.println(MENU_EXIT + ANSI_RESET);

            enteringline = scanner.nextLine();
            if (validate(0, 6, enteringline)) {
                int result = Integer.parseInt(enteringline);

                switch (result) {
                    case 0:
                        System.exit(0);

                    case 1:
                        showAll();
                        backStep();
                        break;

                    case 2:
                        addBook();
                        backStep();
                        break;

                    case 3:
                        deleteBook();
                        backStep();
                        break;

                    case 4: editBook();
                    backStep();
                    break;

                    case 5:
                        searchByTheme();
                        backStep();
                        break;
                }
            } else {
                System.out.println(ANSI_RED + MENU_INCORRECT_INPUT + ANSI_RESET);
            }
        }
    }

    public void searchByTheme() {
        while (true) {
            System.out.println(ANSI_RED + "Введите искомую тему" + ANSI_RED);
            String input = scanner.nextLine();
            boolean flag = false;
            for (Theme theme : bookDao.getThemeList()) {
                if (theme.getName().equals(input))
                    flag = true;
            }
            if (flag) {
                List<String> list = bookDao.getBookNames(input);
                System.out.println(ANSI_GREEN + "Тема  " + ANSI_RESET + ANSI_CYAN + input + ANSI_RESET + ANSI_GREEN + " содержится в следующих книгах:" + ANSI_RESET);
                for (String str : list) {
                    System.out.println(ANSI_CYAN + str);
                }
                break;
            } else {
                System.out.println(ANSI_RED + "Такой темы нет" + ANSI_RED);
            }
        }
    }

    public void deleteBook() {
        System.out.println("Выберете, какую книгу собираетесь удалять");
        int i = 1;
        for (String s : bookDao.getBookTitles()) {
            System.out.println(i++ + ". " + s);
        }

        int number = Integer.parseInt(scanner.nextLine());

        bookDao.deleteBook(bookDao.getBookTitles().get(number - 1));
    }

    public void backStep() {
        while (true) {
            System.out.println(ANSI_RED + "1. Вернуться назад");
            System.out.println("0. Выйти");

            enteringLine = scanner.nextLine();

            if (validate(0, 1, enteringLine)) {
                if (Integer.parseInt(enteringLine) == 1) {
                    break;
                } else {
                    System.exit(0);
                }
            }
        }

    }

    private void addBook() {
        boolean flag = true;
        while (flag) {

            String authorName = "";
            String bookName;


            System.out.println(ANSI_CYAN + MENU_CHOOSE_OPERATION + ANSI_RESET);
            System.out.println(ANSI_CYAN + "1. Создать нового автора");
            System.out.println("2. Выбрать из уже существующих" + ANSI_RESET);

            enteringLine = scanner.nextLine();
            int authorId = 0;
            if (validate(1, 2, enteringLine)) {
                if (Integer.parseInt(enteringLine) == 1) {
                    System.out.println(ANSI_GREEN + " Введите имя нового автора" + ANSI_RESET);
                    String authorname = scanner.nextLine();
                    bookDao.addNewAuthor(authorname);
                } else {
                    System.out.println(ANSI_GREEN + "Выберите уже существующего автора" + ANSI_RESET);
                    bookDao.getAuthorList();

                    for (Author author : bookDao.getAuthorList()) {
                        System.out.println(ANSI_CYAN + author.getId() + ". " + author.getAuthorName() + ANSI_CYAN);
                    }
                    String number = scanner.nextLine();
                    try {
                        for (Author author : bookDao.getAuthorList()) {
                            if (author.getId() == Integer.parseInt(number)) {
                                authorName = author.getAuthorName();
                                authorId = Integer.parseInt(number);
                            }
                        }
                    } catch (Exception e) {
                    }

                    System.out.println(ANSI_GREEN + "Введите название книги" + ANSI_RESET);

                    String bookname = "";
                    try {
                        bookname = scanner.nextLine();
                        if (bookname.equals("")) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    if (!bookname.contains("\\d{}")) {
                        System.out.println(ANSI_GREEN + "Выберите темы" + ANSI_RESET);

                        List<Theme> themes = bookDao.getThemeList();

                        for (Theme theme : themes) {
                            System.out.println(ANSI_GREEN + theme.getId() + " " + theme.getName() + ANSI_RESET);
                        }

                        System.out.println(ANSI_CYAN + "Введите через запятую id тем" + ANSI_CYAN);
                        enteringLine = scanner.nextLine();

                        String[] ids = enteringLine.split(",");
                        List<Integer> themeIds = new ArrayList<>();
                        for (String id : ids) {
                            themeIds.add(Integer.parseInt(id));
                        }

                        Book book = new Book();
                        book.setName(bookname);
                        book.setAuthorName(authorName == null ? authorName : "");

                        bookDao.insertBook(book, themeIds, authorId);

                        flag = false;
                    }
                }
            }


        }
    }


    private void createSpaces() {
        for (int i = 0; i < 2; ++i) {
            System.out.println();
        }
    }

    private boolean validate(int min, int max, String value) {
        if (!(value.matches("\\d") && Integer.parseInt(value) >= min && Integer.parseInt(value) <= max)) {
            return false;
        } else {
            return true;
        }
    }

    public void showAll() {
        try {
            bookDao.readData();
        } catch (SQLException e) {

        }
    }
}
