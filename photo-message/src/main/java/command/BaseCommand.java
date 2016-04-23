package command;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;

abstract class BaseCommand implements Command {
    private static final String HOST_NAME;

    private final CommandType commandType;

    BaseCommand(@NotNull CommandType commandType) {
        this.commandType = commandType;
    }

    @Override
    public CommandType getType() {
        return commandType;
    }

    @Override
    public String getHostName() {
        return HOST_NAME;
    }

    @Override
    public long getDataLength() {
        return 0;
    }

    @Override
    public String toString() {
        return "Command: " + commandType + ", HOST: " + HOST_NAME;

    }

    static {
        String name;
        try {
            name = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            name = "client";
        }
        HOST_NAME = name;
    }
}
