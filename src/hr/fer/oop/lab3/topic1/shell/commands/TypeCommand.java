package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Terminal;
import hr.fer.oop.lab3.topic1.shell.commands.AbstractCommand;
import hr.fer.oop.lab3.topic1.shell.commands.CommandException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luka on 07/12/14.
 */
public class TypeCommand extends AbstractCommand {

    public TypeCommand() {
        super("type", "Prints File content in a shell");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        Path inputPath = Paths.get(inputArguments);
        Terminal terminal = environment.getActiveTerminal();
        Path oldPath = terminal.getCurrentPath();
        Path relativePath = new File(oldPath.toString(), inputArguments).toPath();


        if (!Files.exists(relativePath) && !Files.exists(inputPath)) throw new CommandException("File doesn't exist!");

        Path pathOfFile = Files.exists(relativePath) ? relativePath : inputPath;
        File fileToPrint = new File(pathOfFile.toString());

        if (!fileToPrint.isFile())
            throw new CommandException("Not a file!");

        if (!fileToPrint.canRead())
            throw new CommandException("Can't read from file!");


        try {
            List<String> lines = Files.readAllLines(fileToPrint.toPath(), StandardCharsets.UTF_8);

            for (String line : lines)
                environment.writeln(line);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return CommandStatus.CONTINUE;
    }
}
