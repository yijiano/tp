package seedu.pill.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * A utility class that manages a logger for the application, logging to both
 * console and file. Logs are stored in {@code PillLog.log} under the {@code ./log/} directory.
 * Console logging is disabled ({@code Level.OFF}), while file logging captures all levels.
 */
public class PillLogger {
    private static Logger logger;
    private static final String PATH = "./log/";
    private static final String FILE_NAME = "PillLog.log";

    /**
     * Sets up the logger with a console handler and a file handler. Logs all messages to the file.
     */
    private static void setUpLogger() {
        logger = Logger.getLogger("PillLogger");

        // Disable parent handlers to prevent unintended terminal output
        logger.setUseParentHandlers(false);

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter());
        consoleHandler.setLevel(Level.OFF);
        logger.addHandler(consoleHandler);

        try {
            File dir = new File(PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            Handler fileHandler = new FileHandler(PATH + FILE_NAME, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            // Log to console and carry on with normal app execution
            logger.log(Level.SEVERE, "Logger handler initialization failed", e);
        }
    }

    /**
     * Returns the logger instance, initializing it if necessary.
     *
     * @return the logger instance
     */
    public static Logger getLogger() {
        if (logger == null) {
            setUpLogger();
        }
        return logger;
    }
}
