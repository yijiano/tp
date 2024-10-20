package seedu.pill.util;

import java.io.IOException;
import java.util.logging.*;

public class PillLogger {
    private static Logger logger;
    private static final String PATH = "./log/";
    private static final String FILE_NAME = "PillLog.log";

    private static void setUpLogger() {
        logger = Logger.getLogger("PillLogger");
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.OFF);
        logger.addHandler(consoleHandler);
        try {
            Handler fileHandler = new FileHandler(PATH + FILE_NAME, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            // Log to console and carry on with normal app execution
            logger.log(Level.SEVERE, "Logger handler initialization failed", e);
        }
    }

    public static Logger getLogger() {
        if (logger == null) {
            setUpLogger();
        }
        return logger;
    }
}
