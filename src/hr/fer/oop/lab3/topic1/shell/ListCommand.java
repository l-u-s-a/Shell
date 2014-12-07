package hr.fer.oop.lab3.topic1.shell;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Luka on 07/12/14.
 */
public class ListCommand extends AbstractCommand {

    public ListCommand() {
        super("list", "List directory content");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        Path inputPath = Paths.get(inputArguments);
        Terminal terminal = environment.getActiveTerminal();
        Path oldPath = terminal.getCurrentPath();
        Path relativePath = new File(oldPath.toString(), inputArguments).toPath();
        Path newPath = oldPath;

        if (inputArguments.equals("..")) {
            if (oldPath.getParent() != null)
                newPath = oldPath.getParent();
        }

        else if (Files.exists(relativePath))
            newPath = relativePath;

        else if (Files.exists(inputPath))
            newPath = inputPath;

        String[] subDirs = new File(newPath.toString()).list();

        for (String dir : subDirs)
            environment.write(dir + "    ");

        environment.writeln("");
        return CommandStatus.CONTINUE;
    }
}
