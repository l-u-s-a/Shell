package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Exceptions.CommandException;
import hr.fer.oop.lab3.topic1.shell.Comparators.FileComparator;
import hr.fer.oop.lab3.topic1.shell.Visitors.RegexVisitor;
import hr.fer.oop.lab3.topic1.shell.Visitors.Visitor;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Created by Luka on 30/12/14.
 */
public class DirCommand extends AbstractCommand {

    private Collection<File> listOfFiles;

    public DirCommand() {
        super("dir", "advanced listing directories");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {

        listOfFiles = new ArrayList<>();

        String[] inputList = inputArguments.split(" +");

        ArrayList<String> inputWords = new ArrayList<>();

        for (String word : inputList)
            inputWords.add(word);

        Path currentPath;

        if (inputWords.size() > 0 && Files.exists(Paths.get(inputWords.get(0))) && inputArguments != "") {
            currentPath = Paths.get(inputWords.get(0));
            inputWords.remove(0);
        } else {
            currentPath = environment.getActiveTerminal().getCurrentPath();
        }

        if (inputWords.size() > 0 && inputWords.get(0).contains("/sort=")) {
            Comparator<File> comparator = new FileComparator(inputWords.get(0).split("/sort=")[1]);
            listOfFiles = new TreeSet<>(comparator);
            inputWords.remove(0);
        }

        Visitor visitor = new DirVisitor();
        visitor.visit(currentPath.toFile());


        if (inputWords.size() > 0 && inputWords.get(0).contains("/filter=")) {
            String arg = inputWords.get(0).split("/filter=")[1];
            if (arg.contains("*")) {
                regexFilter(arg);
            } else {
                noRegexFilter(arg);
            }
            inputWords.remove(0);
        }

        if (inputWords.size() > 0 && inputWords.get(0).contains("/type=")) {
            typeFilter(inputWords.get(0).split("/type=")[1]);
            inputWords.remove(0);
        }

        if (inputWords.size() > 0 && inputArguments != "") throw new CommandException("Invalid input");

        if (getListOfFiles().size() > 0) {
            for (File file : getListOfFiles())
                environment.writeln(file.toString());
        } else
            environment.writeln("nothing found...");

        return CommandStatus.CONTINUE;
    }

    private void typeFilter(String s) {
        Iterator<File> fileIterator = getListOfFiles().iterator();
        switch (s) {
            case "f":
                while (fileIterator.hasNext()) {
                    File file = fileIterator.next();
                    if (file.isDirectory())
                        fileIterator.remove();
                }
                break;
            case "d":
                while (fileIterator.hasNext()) {
                    File file = fileIterator.next();
                    if (file.isFile())
                        fileIterator.remove();
                }
                break;
            default:
                throw new CommandException(s + " is not allowed in type filter(d-filter directories, f-filter files)");
        }
    }

    private void noRegexFilter(String arg) {
        Iterator<File> fileIterator = getListOfFiles().iterator();
        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            if (!file.getName().equalsIgnoreCase(arg))
                fileIterator.remove();
        }
    }


    public Collection<File> getListOfFiles() {
        return listOfFiles;
    }

    private void regexFilter(String args) {
        String[] strings = args.split("\\*");
        String leftString = strings[0].toLowerCase();
        String rightString;

        if (strings.length < 2)
            rightString = "";
        else
            rightString = strings[1].toLowerCase();

        Iterator<File> fileIterator = getListOfFiles().iterator();

        while (fileIterator.hasNext()) {
            File file = fileIterator.next();
            String fileName = file.getName().toLowerCase();

            if (!fileName.startsWith(leftString) || !fileName.endsWith(rightString)) {
                fileIterator.remove();
            }
        }
    }

    private class DirVisitor extends Visitor {
        @Override
        protected void whenLeavingDirectory(File file) {
        }

        @Override
        protected void beforeEnteringDirectory(File directory) {
            getListOfFiles().add(directory);
        }

        @Override
        protected void operateWithFile(File current) {
            getListOfFiles().add(current);
        }
    }
}
