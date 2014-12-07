package hr.fer.oop.lab3.topic1.shell;

/**
 * Created by Luka on 06/12/14.
 */
public class QuitCommand extends AbstractCommand{

    public QuitCommand() {
        super("quit", "Close terminal");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        if (inputArguments.length() != 0)
            throw new CommandException("did you mean quit?");
        return CommandStatus.EXIT;
    }
}
