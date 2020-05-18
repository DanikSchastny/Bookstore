package bsu.schastny.lab1;

import bsu.schastny.lab1.model.Book;

import java.util.Comparator;
import java.util.Set;

import static bsu.schastny.constants.ApplicationStringConstants.*;

public class ConsoleService {
    private int authorMax;
    private int titleMax;
    private int themesMax;


    private void printTwoSpaces(){
        System.out.println();
        System.out.println();
    }

    public void print(Set<Book> books){
        System.out.println(ANSI_RED + "Результат запроса :" + ANSI_RESET);
        init(books);
        books.stream().forEach(book -> {
            printLine(book);
        });
        printVerticalBorder();
        printTwoSpaces();
    }



    public void init(Set<Book> books) {
        authorMax = books.stream().max(
                Comparator.comparingInt(book -> book.getAuthorName().length())
        ).get().getAuthorName().length();

        titleMax = books.stream().mapToInt(
                (book) -> book.getName().length()
        ).max().getAsInt();

        themesMax = books.stream().mapToInt(
                (book) -> book.getThemes().stream().mapToInt(
                        (theme) -> theme.getName().length()
                ).max().getAsInt()
        ).max().getAsInt();
    }

    private void printVerticalBorder(){
        for(int i = 0; i < 10 + authorMax + titleMax + themesMax; ++i){
            System.out.print(ANSI_CYAN +  "*" + ANSI_RESET);
        }
        System.out.print("\n");
    }

    private void printLine(Book book){
        printVerticalBorder();
        System.out.print(ANSI_CYAN + "* " + ANSI_RESET);
        System.out.print(ANSI_GREEN + book.getName() + ANSI_RESET);
        for(int i = 0; i < (titleMax - book.getName().length()); ++i){
            System.out.print(" ");
        }
        System.out.print(ANSI_CYAN + " * " + ANSI_RESET);

        System.out.print(ANSI_GREEN + book.getAuthorName() + ANSI_RESET);
        for(int i = 0; i < (authorMax - book.getAuthorName().length()); ++i){
            System.out.print(" ");
        }
        System.out.print(ANSI_CYAN + " * " + ANSI_RESET);

        System.out.print(ANSI_GREEN +  book.getThemes().get(0).getName() + ANSI_RESET);
        for(int i = 0; i < (themesMax - book.getThemes().get(0).getName().length()); ++i){
            System.out.print(" ");
        }
        System.out.print(ANSI_CYAN + " *" + ANSI_RESET);
        System.out.println();

        if(book.getThemes().size() > 1){

            for(int i = 1; i < book.getThemes().size(); ++i){
                System.out.print(ANSI_CYAN + "* " + ANSI_RESET);
                for(int j = 0; j < authorMax + titleMax + 4; ++j){
                    if(j == titleMax + 1){
                        System.out.print(ANSI_CYAN + "*" + ANSI_RESET);
                    }
                    else {
                        System.out.print(" ");
                    }
                }
                System.out.print(ANSI_CYAN + "* " + ANSI_RESET);

                System.out.print(ANSI_GREEN + book.getThemes().get(i).getName() + ANSI_RESET);
                for(int j = 0; j < (themesMax - book.getThemes().get(i).getName().length()); ++j){
                    System.out.print(" ");


                }
                System.out.print(ANSI_CYAN + " *" + ANSI_RESET);
                System.out.println();
            }
        }


    }

}
