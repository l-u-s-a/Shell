package hr.fer.oop.lab3.topic1.shell;

/**
 * Created by Luka on 07/12/14.
 */
public class CopyCommand extends AbstractCommand {

    public CopyCommand() {
        super("copy", "Copies File(only File) from <path1> to <path2>");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {

        String[] paths = inputArguments.split(" ");

        if (paths.length < 2) throw new CommandException("copy needs second argument");

        String firstPath = paths[0];
        String secondPath = paths[1];


        return CommandStatus.CONTINUE;
    }
}
