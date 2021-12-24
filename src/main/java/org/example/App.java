package org.example;

import quiz.api.DataProviderCsv;
import quiz.model.Quiz;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    static DataProviderCsv providerCsv;
    static Quiz quiz;
    static List<Quiz> quizList;

    public App() {
        logger.debug("App()[0]: starting application.........");
    }

    public static void main( String[] args ) {
        /*
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
        logBasicSystemInfo();
         */

        /*
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/enviroment.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.getProperty("home"));
         */

        /*
        String key = "home";
        String properties = null;
        try {
            properties = ConfigurationUtil.getConfigurationEntry(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(properties);
         */
        providerCsv = new DataProviderCsv();
        int choice = 5;
        int id = 1;
        switch (choice){
            case 1:
                for (int i = 0; i < 10; i++) {
                    quiz = new Quiz(i, "guest " + i, "personal " + i);
                    providerCsv.insert(quiz);
                }
                break;
            case 2:
                quizList = providerCsv.selectQuiz();
                quizList.forEach(logger::info);
                break;
            case 3:
                quiz = providerCsv.getById(id);
                logger.info(quiz);
                break;
            case 4:
                providerCsv.delete(id);
                break;
            case 5:
                quiz = new Quiz(10, "guest 10", "personal 10");
                providerCsv.update(quiz);
                break;
        }
    }

    private static void logBasicSystemInfo() {
        logger.info("Launching the application...");
        logger.info(
                "Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version")
        );
        logger.info("JRE: " + System.getProperty("java.version"));
        logger.info("Java Launched From: " + System.getProperty("java.home"));
        logger.info("Class Path: " + System.getProperty("java.class.path"));
        logger.info("Library Path: " + System.getProperty("java.library.path"));
        logger.info("User Home Directory: " + System.getProperty("user.home"));
        logger.info("User Working Directory: " + System.getProperty("user.dir"));
        logger.info("Test INFO logging.");
    }
}
