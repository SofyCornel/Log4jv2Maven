package quiz.api;

import com.google.gson.Gson;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import quiz.model.HistoryContent;
import quiz.model.Quiz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class DataProvider {
    abstract public Quiz getById(long id);

    abstract public List<Quiz> selectQuiz();

    abstract public boolean insert(Quiz quiz);

    abstract public boolean delete(long id);

    abstract public boolean update(Quiz quiz);

    abstract public void clear();

    public void saveHistory(String className, String methodName, HistoryContent.Status status, Quiz json){
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("history");
            try {
                database.createCollection("historyContent");
            } catch (MongoCommandException ignored) {
            }
            String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
            HistoryContent historyContent = new HistoryContent(className, date, methodName, status, new Gson().toJson(json));
            MongoCollection<Document> collection = database.getCollection("historyContent");
            collection.insertOne(Document.parse(new Gson().toJson(historyContent)));
        }
    }
}
