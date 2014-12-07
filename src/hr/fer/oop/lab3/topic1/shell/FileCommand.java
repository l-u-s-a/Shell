package hr.fer.oop.lab3.topic1.shell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luka on 07/12/14.
 */
public class FileCommand extends AbstractCommand {

    public FileCommand() {
        super("type", "Prints File content in a shell");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        Path inputPath = Paths.get(inputArguments);
        Terminal terminal = environment.getActiveTerminal();
        Path oldPath = terminal.getCurrentPath();
        Path relativePath = new File(oldPath.toString(), inputArguments).toPath();


        if (Files.exists(relativePath) || Files.exists(inputPath)) {
            Path pathOfFile = Files.exists(relativePath) ? relativePath : inputPath;
            File fileToPrint = new File(pathOfFile.toString());

            if (!fileToPrint.isFile())
                throw new CommandException("Not a file!");

            else if (!fileToPrint.canRead())
                throw new CommandException("Can't read from file!");

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToPrint), "UTF-8"));
                String txtLine;

                while ((txtLine = reader.readLine()) != null)
                    environment.writeln(txtLine);

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else
            throw new CommandException("File doesn't exist!");

        return CommandStatus.CONTINUE;
    }
}
