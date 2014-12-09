package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.ShellCommand;

/**
 * Created by Luka on 05/12/14.
 */
public abstract class AbstractCommand implements ShellCommand {

    private String commandName;
    private String commandDescription;

    public AbstractCommand(String commandName, String commandDescription) {
        this.commandName = commandName;
        this.commandDescription = commandDescription;
    }


    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public String getCommandDescription() {
        return commandDescription;
    }


}
