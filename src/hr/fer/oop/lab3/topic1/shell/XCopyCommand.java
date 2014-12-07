package hr.fer.oop.lab3.topic1.shell;

/**
 * Created by Luka on 07/12/14.
 */
public class XCopyCommand extends AbstractCommand {

    public XCopyCommand() {
        super("xcopy", "Copies recursively directory from <path1> to <path2>");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        return CommandStatus.CONTINUE;
    }
}
