package command.client;

import command.Command;
import command.CommandExecutor;
import command.CommandType;
import command.PingCommand;
import connection.Connection;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;


public class ClientCommandExecutor implements CommandExecutor {
    private final EnumMap<CommandType, CommandExecutor> commandWorkers;

    public ClientCommandExecutor() {
        commandWorkers = new EnumMap<>(CommandType.class);
        commandWorkers.put(CommandType.PING,
                (command, connection) -> {
                    connection.writeCommand(command);
                    Command result = connection.readCommand();
                    if (result != null && result instanceof PingCommand) {
                        System.out.println(result);
                    }
                    return false;
                });
        commandWorkers.put(CommandType.TERMINATE,
                (command, connection) -> false);
    }

    @Override
    public boolean execute(@NotNull Command command, @NotNull Connection connection) {
        CommandExecutor executor = commandWorkers.get(command.getType());
        return executor != null && executor.execute(command, connection);
    }
}
