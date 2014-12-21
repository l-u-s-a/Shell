package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Exceptions.CommandException;

/**
 * Created by Luka on 06/12/14.
 */
public class QuitCommand extends AbstractCommand {

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
