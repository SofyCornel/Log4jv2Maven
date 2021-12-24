package org.example;

import org.junit.Test;
import quiz.api.DataProvider;
import quiz.api.DataProviderDB;
import quiz.api.DataProviderXml;
import quiz.model.Quiz;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    DataProvider dp = new DataProviderDB();

    @Test
    public void testGetById(){
        Quiz quiz = new Quiz(1,"guest 1", "personal 1");
        dp.insert(quiz);
        Quiz byId = dp.getById(quiz.getId());
        assertEquals(quiz, byId);
    }

    @Test
    public void getByIdWrong(){
        Quiz byId = dp.getById(-1);
        assertEquals(0, byId.getId());
    }

    @Test
    public void testSelect(){
        Quiz quiz = new Quiz(2,"guest 2", "personal 2");
        dp.insert(quiz);
        List<Quiz> QuizList = dp.selectQuiz();
        assertEquals(quiz, QuizList.get(QuizList.size()-1));
    }

    @Test
    public void testUpdate(){
        Quiz quiz = new Quiz(3,"guest 3", "personal 3");
        Quiz newQuiz = new Quiz(quiz.getId(), "guest 3.1", "personal 3.1");
        dp.insert(quiz);
        dp.update(newQuiz);
        Quiz byId = dp.getById(quiz.getId());
        assertEquals(newQuiz, byId);
    }

    @Test
    public void updateWrong(){
        assertFalse(dp.update(new Quiz(-1)));
    }

    @Test
    public void testDelete(){
        Quiz quiz = new Quiz(4,"guest 4", "personal 4");
        dp.insert(quiz);
        assertTrue( dp.delete(4));
    }

    @Test
    public void deleteWrong(){
        assertFalse(dp.delete(-1));
    }

    //@Test
    public void clear(){
        dp.clear();
    }
}
