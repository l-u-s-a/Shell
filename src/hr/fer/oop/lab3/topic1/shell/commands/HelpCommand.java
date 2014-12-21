package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.ShellCommand;
import hr.fer.oop.lab3.topic1.shell.Exceptions.CommandException;

/**
 * Created by Luka on 06/12/14.
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super("help", "List commands with their description");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        if (inputArguments.length() != 0)
            throw new CommandException("did you mean help?");

        environment.writeln("");
        environment.writeln("Here's a list of functions. I hope it will help you:");

        Iterable<ShellCommand> commandsHashTable = environment.commands();

        for (ShellCommand command: commandsHashTable){
            environment.writeln("   " + command.getCommandName() + calculateSpaces(command.getCommandName()) + command.getCommandDescription());
        }
        return CommandStatus.CONTINUE;
    }

    protected String calculateSpaces(String commandName) {
        int requiredSpaces = 16 - commandName.length();

        StringBuilder spaces = new StringBuilder();
        for (int i = 1; i <= requiredSpaces; i++)
            spaces.append(" ");

        return spaces.toString();
    }
}
