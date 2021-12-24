package quiz.api;

import quiz.model.Quiz;

import java.util.List;

public interface IDataProvider {
    Quiz getById(long id);
    List<Quiz> selectQuiz();
    void insert(Quiz quiz);
    void delete(long id);
    void update(Quiz quiz);

}
