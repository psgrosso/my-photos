package command.server;

import command.Command;
import command.CommandExecutor;
import command.CommandType;
import connection.Connection;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ServerCommandProcessor extends Thread {
    private final CommandExecutor commandExecutor;
    private final Connection connection;

    public ServerCommandProcessor(@NotNull CommandExecutor commandExecutor, @NotNull Connection connection) {
        this.commandExecutor = commandExecutor;
        this.connection = connection;
        start();
    }

    @Override
    public void run() {
        InputStream inputStream = connection.getInputStream();
        boolean terminate = false;
        while (!terminate) {
            Command command = connection.readCommand();
            terminate = commandExecutor.execute(command, connection);
        }
    }
}
