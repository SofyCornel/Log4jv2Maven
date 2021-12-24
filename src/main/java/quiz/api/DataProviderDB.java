package quiz.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import quiz.model.HistoryContent;
import quiz.model.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataProviderDB extends DataProvider{
    private static final Logger logger = LogManager.getLogger(DataProviderDB.class);
    @Override
    public Quiz getById(long id) {
        Quiz quiz = new Quiz();
        try (Connection connection = DriverManager.getConnection(Constants.ADDRESS + Constants.DB_NAME,
                Constants.USER, Constants.PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(Constants.GET_BY_ID, id));
            if (resultSet.next()) {
                quiz.setId(resultSet.getLong(1));
                quiz.setGuest(resultSet.getString(2));
                quiz.setPersonal(resultSet.getString(3));
            }
        } catch (SQLException e) {
            logger.error(e);
            return null;
        }
        return quiz;
    }

    @Override
    public List<Quiz> selectQuiz() {
        List<Quiz> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(Constants.ADDRESS + Constants.DB_NAME,
                Constants.USER, Constants.PASSWORD)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Constants.SELECT_ALL);
            while (resultSet.next()){
                Quiz entity = new Quiz(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
                list.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return list;
    }

    @Override
    public boolean insert(Quiz quiz) {
        if (quiz == null){
            return false;
        }
        String query = String.format(Constants.INSERT, quiz.getId(), quiz.getGuest(), quiz.getPersonal());
        execute(Constants.CREATE_TABLE, query);
        saveHistory(getClass().getName(), "insert", HistoryContent.Status.SUCCESS, quiz);
        return true;
    }

    @Override
    public boolean delete(long id) {
        if (getById(id).getId() == 0){
            return false;
        }
        String query = String.format(Constants.DELETE, id);
        execute(Constants.CREATE_TABLE, query);
        return true;
    }

    @Override
    public boolean update(Quiz quiz) {
        Quiz byId = getById(quiz.getId());
        if (byId.getId() == 0){
            return false;
        }
        String query = String.format(Constants.UPDATE, quiz.getGuest(), quiz.getPersonal(), quiz.getId());
        execute(Constants.CREATE_TABLE, query);
        saveHistory(getClass().getName(), "update", HistoryContent.Status.SUCCESS, quiz);
        return true;
    }

    public void clear(){
        execute(Constants.CREATE_TABLE, Constants.CLEAR);
    }

    public static void execute(String table, String query) {
        try {
            Class.forName(Constants.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error(e);
        }
        try (Connection connection = DriverManager.getConnection(Constants.ADDRESS + Constants.DB_NAME,
                Constants.USER, Constants.PASSWORD)){
            Statement statement = connection.createStatement();
            // создание таблицы, если она не существует
            statement.execute(table);
            // выполнение запроса
            statement.execute(query);
        } catch (SQLException e) {
            logger.error(e);
        }
    }

}
