package connection;

import command.Command;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.io.OutputStream;


public interface Connection {
    InputStream getInputStream();
    OutputStream getOutputStream();

    /**
     * Writes a command a waits for an acknowledge
     * @param command the command to be sent
     * @return true if the command was successfully written
     */
    boolean writeCommand(@NotNull Command command);

    /**
     * Waits for a command to be received and once read sends an acknowledge to the counterpart
     * @return the command just read
     */
    Command readCommand();

    /**
     * Sends an acknowledge
     * @return true if the acknowledge was successfully sent
     */
    boolean acknowledge();

    /**
     * Waits until an acknowledge has been received
     * @return true if the acknowledge was successfully received
     */
    boolean waitAcknowledge();
}
