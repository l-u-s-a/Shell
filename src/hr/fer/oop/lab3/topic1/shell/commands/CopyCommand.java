package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.commands.AbstractCommand;
import hr.fer.oop.lab3.topic1.shell.commands.CommandException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;


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
        String firstPath = paths[0];
        File inputFile = new File(firstPath);

        if (paths.length < 2) throw new CommandException("copy needs second argument");

        String secondPath = paths[1];
        File outputFile = new File(secondPath);

        check(inputFile);

        if (outputFile.isDirectory())
            outputFile = new File(outputFile, inputFile.getName());

        if (outputFile.getParentFile().isDirectory()) {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = Files.newInputStream(inputFile.toPath(), StandardOpenOption.READ);
                outputStream = Files.newOutputStream(outputFile.toPath(), StandardOpenOption.CREATE);

                byte[] buffer = new byte[1024];
                while (inputStream.read(buffer) > -1) {
                    outputStream.write(buffer);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else
            throw new CommandException("second path doesn't exist");

        return CommandStatus.CONTINUE;
    }

    private void check(File inputFile) {
        if (!inputFile.exists()) throw new CommandException("File doesn't exist");

        if (inputFile.isDirectory()) throw new CommandException("cannot copy directory");

        if (!inputFile.canRead()) throw new CommandException("File is not readable.");
    }
}
