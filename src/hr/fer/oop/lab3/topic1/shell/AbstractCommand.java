package hr.fer.oop.lab3.topic1.shell;

/**
 * Created by Luka on 05/12/14.
 */
public class AbstractCommand implements ShellComand {
    @Override
    public String getCommandName() {
        return null;
    }

    @Override
    public String getCommandDescription() {
        return null;
    }

    @Override
    public CommandStatus execute() {
        return null;
    }
}
