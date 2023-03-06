import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ParallelProcessing {
    public static void main(String[] args) {}
}

class MyLogger {
    private Logger LOGGER;
    private Level LOGGER_LEVEL = Level.OFF;

    private void configureLogger() {
        LOGGER.setLevel(LOGGER_LEVEL);

        LOGGER.setUseParentHandlers(false);

        SimpleFormatter simpleFormatter =
                new SimpleFormatter() {
                    @Override
                    public String format(LogRecord logRecord) {
                        return String.format(
                                "[%-7s] - %s \n", logRecord.getLevel(), logRecord.getMessage());
                    }
                };

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(simpleFormatter);

        LOGGER.addHandler(consoleHandler);
    }

    public <T> MyLogger(Class<T> myClass) {
        LOGGER = Logger.getLogger(myClass.getName());

        configureLogger();
    }

    public Logger getLogger() {
        return LOGGER;
    }
}
