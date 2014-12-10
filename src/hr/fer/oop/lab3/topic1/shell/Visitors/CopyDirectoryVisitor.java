package hr.fer.oop.lab3.topic1.shell.Visitors;

import hr.fer.oop.lab3.topic1.shell.Visitors.Visitor;
import hr.fer.oop.lab3.topic1.shell.commands.CopyCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


/**
 * Created by Luka on 09/12/14.
 */
public class CopyDirectoryVisitor extends Visitor {
    private File outputDirectory;

    public CopyDirectoryVisitor(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    protected void whenLeavingDirectory(File file) {
        outputDirectory = outputDirectory.getParentFile();
    }

    @Override
    protected void beforeEnteringDirectory(File inputDirectory) {
         if (!outputDirectory.exists()) {
            try {
                Files.createDirectory(outputDirectory.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File newDir = new File(outputDirectory, inputDirectory.getName());
            newDir.mkdir();
            outputDirectory = newDir;
        }


    }

    @Override
    protected void operateWithFile(File inputFile) {
        File newFile = new File(outputDirectory, inputFile.getName());

        new CopyCommand().execute(null, inputFile.toString() + " " + newFile.toString());

    }
}
