package hr.fer.oop.lab3.topic1.shell;

import java.io.File;

/**
 * Created by Luka on 07/12/14.
 */
public class NoRegexVisitor extends Visitor {
    private String fileName;
    private Environment environment;

    public NoRegexVisitor(String fileName, Environment environment) {
        this.fileName = fileName;
        this.environment = environment;
    }

    @Override
    protected void whenLeavingDirectory(File file) {

    }

    @Override
    protected void beforeEnteringDirectory(File directory) {

    }

    @Override
    protected void operateWithFile(File current) {
        if (current.getName().equalsIgnoreCase(fileName))
            environment.writeln(current.getPath());
    }
}
