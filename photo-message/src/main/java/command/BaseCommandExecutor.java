package command;


public abstract class BaseCommandExecutor implements CommandExecutor {
    private final CommandType commandType;

    protected BaseCommandExecutor(CommandType commandType) {
        this.commandType = commandType;
    }

    protected void checkCommand(Command command) {
        if (command.getType() != commandType) {
            throw new IllegalArgumentException("Invalid command: " + command + ", expected: " + commandType);
        }
    }
}
