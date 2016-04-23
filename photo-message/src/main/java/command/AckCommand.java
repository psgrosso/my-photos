package command;


/**
 * Immutable Acknowledge command
 */
public final class AckCommand extends BaseCommand {
    private static final Command ackCommand = new AckCommand();

    private AckCommand() {
        super(CommandType.ACKNOWLEDGE);
    }

    public static Command getInstance() {
        return ackCommand;
    }
}
