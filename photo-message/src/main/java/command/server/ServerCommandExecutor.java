package command.server;

import command.Command;
import command.CommandType;
import command.CommandExecutor;
import connection.Connection;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;


public class ServerCommandExecutor implements CommandExecutor {
    private static final CommandExecutor serverCommandExecutor = new ServerCommandExecutor();
    private static final EnumMap<CommandType, CommandExecutor> commandWorkers;

    private ServerCommandExecutor() {}

    @Override
    public boolean execute(@NotNull Command command, @NotNull Connection connection) {
        CommandExecutor worker = commandWorkers.get(command.getType());
        if (worker == null) {
            throw new RuntimeException("No server worker found for " + command.getType());
        }
        return worker.execute(command, connection);
    }

    public static CommandExecutor getInstance() {
        return serverCommandExecutor;
    }

    static {
        CommandExecutor terminateCommand = (connection, command) -> false;
        CommandExecutor showCommand = (connection, command) -> {
            System.out.println("show command!");
            return true;
        };
        CommandExecutor pingCommand = (command, connection) -> {
            System.out.println("PING!!!");
            connection.acknowledge();
            return true;
        };

        commandWorkers = new EnumMap<>(CommandType.class);
        commandWorkers.put(CommandType.TERMINATE, terminateCommand);
        commandWorkers.put(CommandType.PING, pingCommand);
    }
}
