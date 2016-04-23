package connection;

import command.AckCommand;
import command.Command;
import command.CommandType;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public final class SocketConnection implements Connection {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SocketConnection(@NotNull Socket socket) throws IOException {
        inputStream = new BufferedInputStream(socket.getInputStream());
        outputStream = new BufferedOutputStream(socket.getOutputStream());
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public boolean writeCommand(@NotNull Command command) {
        return sendCommand(command) && waitAcknowledge();
    }

    @Override
    public Command readCommand() {
        Command command = receiveCommand();
        if (command != null && acknowledge()) {
            return command;
        }
        return null;
    }

    @Override
    public boolean acknowledge() {
        return sendCommand(AckCommand.getInstance());
    }

    @Override
    public boolean waitAcknowledge() {
        Command command = receiveCommand();
        return command != null && command.getType() == CommandType.ACKNOWLEDGE;
    }

    private boolean sendCommand(@NotNull Command command) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.outputStream);
            objectOutputStream.writeObject(command);
            objectOutputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Command receiveCommand() {
        try {
            Object rawObject = new ObjectInputStream(inputStream).readObject();
            if (rawObject instanceof Command) {
                return (Command) rawObject;
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return null;
    }
}
