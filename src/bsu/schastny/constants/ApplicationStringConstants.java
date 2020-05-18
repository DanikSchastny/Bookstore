package bsu.schastny.constants;

public class ApplicationStringConstants {

    // <--- Database config constants --->
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "cef_a615ZFC62";
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false&allowMultiQueries=true";

    // <--- Menu constants --->

    public static final String MENU_CHOOSE_OPERATION = "Выберите номер операции";
    public static final String MENU_SHOW = "1. Показать все";
    public static final String MENU_ADD = "2. Добавить книгу";
    public static final String MENU_DELETE = "3. Удалить книгу";
    public static final String MENU_EDIT = "4. Изменить книгу";
    public static final String MENU_SEARCH_BY_THEME = "5. Поиск книг по теме";
    public static final String MENU_EXIT = "0. выйти";
    public static final String MENU_INCORRECT_INPUT = "Некорректно введен ответ";

    public static final String SHOW_BOOKS = "1. Показать все книги";
    public static final String SHOW_AUTHORS = "2. Показать всех авторов";
    public static final String SHOW_THEMES = "3. Показать все темы";
    public static final String BACK_TO_MAIN_MENU = "0. Вернуться в главное меню";
    public static final String SHOW_ALL = "4. Показать все";


    // <--- Colors for console --->

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    // <--- SQL --->

    public static final String AUTHOR_ID = "author_id";
    public static final String AUTHOR_NAME = "author_name";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_NAME = "book_name";
    public static final String THEME_ID = "theme_id";
    public static final String THEME_NAME = "theme_name";

    public static final String NATURAL_JOIN_ALL =
            "SELECT author_name, book_name, theme_name" +
            "natural join (" +
            "books join book_theme on books.book_id = book_theme.book_ID" +
            "join themes on book_theme.theme_ID = themes.theme_id) ORDER BY author_name, book_name,theme_name";

    public static final String GET_ALL_STATEMENT =
            "SELECT author_name, book_name,theme_name,authors.author_id,books.book_id, themes.theme_id FROM" +
            "authors natural join(" +
            "books join book_theme on books.book_id = book_theme.book_ID join" +
            "themes on book_theme.theme_ID = themes.theme_id "+
            "themes on book_theme.theme_ID = themes.theme_id) ORDER BY authors.author_id, author_name, books.book_id, book_name, themes.theme_id, theme_name";

}
