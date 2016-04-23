package command;

import connection.Connection;
import org.jetbrains.annotations.NotNull;

/**
 * Functional interface that declares the way that a command has to be executed
 */
public interface CommandExecutor {

    /**
     * Executes the specified command, possibly sending a response through the connection
     * @param command the command to be executed
     * @param connection the connection to be used to read information and to send a response
     * @return true if the command was successful
     */
    boolean execute(@NotNull Command command, @NotNull Connection connection);
}
