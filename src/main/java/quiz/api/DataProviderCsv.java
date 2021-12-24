package quiz.api;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import quiz.model.Quiz;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DataProviderCsv implements IDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderCsv() {
    }
    @Override
    public Quiz getById(long id) {
        List<Quiz> quizList = selectQuiz();
        Quiz quiz = null;
        try {
            quiz = quizList.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            logger.throwing(e);
        }
        return quiz;
    }

    @Override
    public List<Quiz> selectQuiz() {
        List<Quiz> beans = new ArrayList<>();
        if (read(Quiz.class, Constants.CSV_PATH).isPresent()){
            beans = (List<Quiz>) (Object) read(Quiz.class, Constants.CSV_PATH).get();
        }
        return beans;
    }

    @Override
    public void insert(Quiz quiz) {
        List<Quiz> list = selectQuiz();
        list.add(quiz);
        save(list, Constants.CSV_PATH);
    }

    @Override
    public void delete(long id) {
        Quiz byId = getById(id);
        if (byId == null){
            return;
        }
        List<Quiz> quizList = selectQuiz();
        quizList.removeIf(a -> (a.getId() == id));
        save(quizList, Constants.CSV_PATH);
    }

    @Override
    public void update(Quiz quiz) {
        Quiz byId = getById(quiz.getId());
        if (byId == null){
            return;
        }
        delete(quiz.getId());
        insert(quiz);
    }

    public static <T> boolean save(List<T> list, String path){
        boolean isSaved;
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(path));
            // превращаем наш bean в Csv строку
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            // записываем в файл
            beanToCsv.write(list);
            csvWriter.close();
            isSaved = true;
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
            isSaved = false;
        }
        return isSaved;
    }

    public static <T> Optional<List<T>> read(Class<?> type, String path){
        List<T> list;
        File file = new File(path);
        Optional<List<T>> optionalList;
        // если в файле есть данные
        if ((file.length() != 0) && (file.exists())){
            try {
                // считываем из файла в bean, класс которогу указан в type
                list = new CsvToBeanBuilder<T>(new FileReader(path)).withType((Class<? extends T>) type).build().parse();
                optionalList = Optional.of(list);
            } catch (FileNotFoundException e) {
                logger.error(e);
                optionalList = Optional.empty();
            }
        } else{
            optionalList = Optional.empty();
        }
        return optionalList;
    }

}
