package hr.fer.oop.lab3.topic1.shell.Visitors;

import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.File;

/**
 * Created by Luka on 07/12/14.
 */
public class RegexVisitor extends Visitor {
    private String leftString;
    private String rightString;
    private Environment environment;


    public RegexVisitor(String fileName, Environment environment) {
        initializeStrings(fileName);
        this.environment = environment;
    }

    private void initializeStrings(String inputArguments) {
        String[] strings = inputArguments.split("\\*");

        leftString = strings[0].toLowerCase();

        if (strings.length < 2)
            rightString = "";
        else
            rightString = strings[1].toLowerCase();

    }

    @Override
    protected void whenLeavingDirectory(File file) {

    }

    @Override
    protected void beforeEnteringDirectory(File directory) {

    }

    @Override
    protected void operateWithFile(File current) {
        String fileName = current.getName().toLowerCase();

        if (fileName.startsWith(leftString) && fileName.endsWith(rightString))
            environment.writeln(current.getPath());
    }
}
