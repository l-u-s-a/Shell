package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.*;
import hr.fer.oop.lab3.topic1.shell.Visitors.NoRegexVisitor;
import hr.fer.oop.lab3.topic1.shell.Visitors.RegexVisitor;
import hr.fer.oop.lab3.topic1.shell.Visitors.Visitor;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by Luka on 07/12/14.
 */
public class FilterCommand extends AbstractCommand {

    public FilterCommand() {
        super("filter", "Search current dir and all sub-dirs for required File");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        Terminal terminal = environment.getActiveTerminal();
        Path path = terminal.getCurrentPath();
        File current = new File(path.toString());
        Visitor visitor;

        if (!inputArguments.contains("*"))
            visitor = new NoRegexVisitor(inputArguments, environment);

        else
            visitor = new RegexVisitor(inputArguments, environment);

        visitor.visit(current);


        return CommandStatus.CONTINUE;
    }
}
