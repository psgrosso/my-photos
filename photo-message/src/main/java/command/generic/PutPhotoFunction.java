package command.generic;

import command.BaseCommandExecutor;
import command.Command;
import command.CommandType;
import connection.Connection;
import org.jetbrains.annotations.NotNull;


public final class PutPhotoFunction extends BaseCommandExecutor {

    public PutPhotoFunction() {
        super(CommandType.PUT_PHOTO);
    }

    @Override
    public boolean execute(@NotNull Command command, @NotNull Connection connection) {
        checkCommand(command);
        return false;
    }
}
