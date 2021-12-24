package org.example;

public class Constants {
    public static final String PACKAGE = "org.example";
    public static final String PROPERTIES_PATH = "properties";
    public static final String CSV_PATH = "quiz.csv";
    public static final String XML_PATH = "quiz.xml";
    public static final String QUIZ = "quiz";
    public static final String QUIZZES = "quizzes";
    public static final String ID = "id";
    public static final String GUEST = "guest";
    public static final String PERSONAL = "personal";
    public static final String ACTOR = "system";

    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String ADDRESS = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "sofia";
    public static final String USER = "root";
    public static final String PASSWORD = "Huy228228_";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS Quiz (" +
            "id SERIAL, " +
            "guest TEXT, " +
            "personal TEXT);";
    public static final String INSERT = "INSERT INTO Quiz (id, guest, personal) VALUES (%d, '%s', '%s');";
    public static final String GET_BY_ID = "SELECT * FROM Quiz WHERE id = %d;";
    public static final String SELECT_ALL = "SELECT * FROM Quiz;";
    public static final String DELETE = "DELETE FROM Quiz WHERE id = %d;";
    public static final String UPDATE = "UPDATE Quiz SET guest = '%s', personal = '%s' WHERE id = %d;";
    public static final String CLEAR = "TRUNCATE TABLE Quiz;";
}
