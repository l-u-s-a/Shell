package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Visitors.CopyDirectoryVisitor;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Visitors.Visitor;

import java.io.File;

/**
 * Created by Luka on 07/12/14.
 */
public class XCopyCommand extends AbstractCommand {

    public XCopyCommand() {
        super("xcopy", "Copies recursively directory from <path1> to <path2>");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {


        String[] paths = inputArguments.split(" ");

        File inputFile = new File(paths[0]);

        if (!inputFile.exists() || !inputFile.isDirectory()) throw new CommandException("first argument must be directory");

        if (paths.length < 2) throw new CommandException("xcopy needs second argument");

        File outputFile = new File(paths[1]);

        if (!outputFile.isDirectory() && !outputFile.getParentFile().isDirectory())
            throw new CommandException("second path nor his parent are dirs");


        Visitor visitor = new CopyDirectoryVisitor(outputFile);

        visitor.Visit(inputFile);


        return CommandStatus.CONTINUE;
    }
}
