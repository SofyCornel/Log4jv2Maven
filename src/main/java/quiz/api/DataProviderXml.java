package quiz.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;
import quiz.model.HistoryContent;
import quiz.model.JAXBCollection;
import quiz.model.Quiz;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DataProviderXml extends DataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderXml.class);
    @Override
    public Quiz getById(long id) {
        List<Quiz> quizList = selectQuiz();
        Quiz quiz;
        try {
            quiz = quizList.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            logger.error(e);
            return null;
        }
        return quiz;
    }

    @Override
    public List<Quiz> selectQuiz() {
        List<Quiz> quizList = new ArrayList<>();
        if (read(Quiz.class, Constants.XML_PATH).isPresent()) {
            quizList = read(Quiz.class, Constants.XML_PATH).get();
        }
        return quizList;
    }

    @Override
    public boolean insert(Quiz quiz) {
        if (quiz == null){
            return false;
        }
        List<Quiz> quizList = selectQuiz();
        quizList.add(quiz);
        save(Constants.QUIZZES, quizList, Constants.XML_PATH);
        saveHistory(getClass().getName(), "insert", HistoryContent.Status.SUCCESS, quiz);
        return true;
    }

    @Override
    public boolean delete(long id) {
        Quiz byId = getById(id);
        if (byId == null){
            return false;
        }
        List<Quiz> quizList = selectQuiz();
        quizList.removeIf(a -> (a.getId() == id));
        save(Constants.QUIZZES, quizList, Constants.XML_PATH);
        saveHistory(getClass().getName(), "delete", HistoryContent.Status.SUCCESS, byId);
        return true;
    }

    @Override
    public boolean update(Quiz quiz) {
        Quiz byId = getById(quiz.getId());
        if (byId == null){
            return false;
        }
        delete(quiz.getId());
        insert(quiz);
        return true;
    }

    @Override
    public void clear() {
        new File(Constants.XML_PATH).delete();
    }

    // https://gist.github.com/pixelase/45c361e6d6c49c9f05faa1bd98bcdce4 (source code)
    public static <T> boolean save(String rootName, Collection<T> collection, String path) {
        boolean isSaved = false;
        try {
            // создаем контекст с найденными классами
            JAXBContext jaxbContext = JAXBContext.newInstance(findTypes(collection));
            Marshaller marshaller = jaxbContext.createMarshaller();
            // делает форматированный файл, чтоб всё НЕ записывалось в одну строку
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // создаем wrapper (оболочку) к нашему List
            JAXBElement collectionElement = createCollectionElement(rootName, collection);
            // записываем в файл
            marshaller.marshal(collectionElement, new File(path));
            isSaved = true;
        } catch (JAXBException e) {
            logger.error(e);
        }
        return isSaved;
    }


    public static <T> Optional<List<T>> read(Class<T> tClass, String path) {
        Optional<List<T>> optional = Optional.empty();
        File file = new File(path);
        try {
            // создаем контекст с типом нашего класса
            JAXBContext jaxbContext = JAXBContext.newInstance(JAXBCollection.class, tClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            // считываем в JAXBCollection
            JAXBCollection<T> collection = unmarshaller.unmarshal(new StreamSource(new File(path)),
                    JAXBCollection.class).getValue();
            // переводим JAXBCollection в List и оборачиваем в Optional
            optional = Optional.of(collection.getItems());
        } catch (JAXBException e) {
            logger.error(e);
        }
        return optional;
    }

    protected static <T> Class[] findTypes(Collection<T> collection) {
        Set<Class> types = new HashSet<>();
        types.add(JAXBCollection.class);
        for (T object : collection) {
            if (object != null) {
                types.add(object.getClass());
            }
        }
        return types.toArray(new Class[0]);
    }

    protected static <T> JAXBElement createCollectionElement(String rootName, Collection<T> tCollection) {
        JAXBCollection collection = new JAXBCollection(tCollection);
        return new JAXBElement<JAXBCollection>(new QName(rootName), JAXBCollection.class, collection);
    }
}
