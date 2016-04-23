package log;


import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class Log {
    public final static Logger CLIENT;
    public final static Logger SERVER;

    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            LogManager.getLogManager().readConfiguration(classLoader.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        CLIENT = Logger.getLogger("CLIENT");
        SERVER = Logger.getLogger("SERVER");
    }
}
