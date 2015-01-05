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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Luka on 30/12/14.
 */
public class DirCommand extends AbstractCommand {

    List<File> listOfFiles = new ArrayList<>();

    public DirCommand() {
        super("dir", "advanced listing directories");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {

//        if (inputArguments == "") throw new CommandException("dir needs at least one argument!");

        String[] inputList = inputArguments.split(" +");

        ArrayList<String> inputWords = new ArrayList<>();

        for (String word : inputList)
            inputWords.add(word);

        Comparator<File> comparator = null;

        Visitor visitor = new DirVisitor();

        Path currentPath;

        if (!Files.exists(Paths.get(inputWords.get(0))) || inputArguments == "")
            currentPath = environment.getActiveTerminal().getCurrentPath();
        else {
            currentPath = Paths.get(inputWords.get(0));
            inputWords.remove(0);
        }

        visitor.visit(currentPath.toFile());

        for (String word : inputWords) {

            if (word.contains("sort=")) {
                comparator = new FileComparator(word.split("/sort=")[1]);
            }

            else if (word.split("/filter=").length > 0) {
                String arg = word.split("/filter=")[1];
                if (arg.contains("*")) {
                    regexFilter(listOfFiles, arg);
                }
                else {
                    noRegexFilter(listOfFiles, arg);
                }
            }
            else if (word.split("/type=").length > 0) {
                typeFilter(word.split("/type=")[1]);
            }

            System.out.println("tu sam");
        }

//        TreeSet<File> set = new TreeSet<>(comparator);
//
//        //print all files
//        for (File file : set)
//            environment.writeln(file.toString());

        return CommandStatus.CONTINUE;
    }

    private void typeFilter(String s) {
        List<File> filteredList = new ArrayList<>();
        switch (s) {
            case "f":
                for (File file : getListOfFiles())
                    if (file.isFile()) filteredList.add(file);
                break;
            case "d":
                for (File file : getListOfFiles())
                    if (file.isDirectory()) filteredList.add(file);
                break;
        }
        setListOfFiles(filteredList);
    }

    private void noRegexFilter(List<File> listOfFiles, String arg) {
        List<File> filteredList = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.getName().equalsIgnoreCase(arg))
                filteredList.add(file);
        }
        setListOfFiles(filteredList);
    }

    public void setListOfFiles(List<File> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    public List<File> getListOfFiles() {
        return listOfFiles;
    }

    private void regexFilter(List<File> files, String args) {
        List<File> filteredList = new ArrayList<>();
        String[] strings = args.split("\\*");
        String leftString = strings[0].toLowerCase();
        String rightString;

        if (strings.length < 2)
            rightString = "";
        else
            rightString = strings[1].toLowerCase();

        for (File file : files) {
            String fileName = file.getName().toLowerCase();

            if (fileName.startsWith(leftString) && fileName.endsWith(rightString))
                filteredList.add(file);
        }
        setListOfFiles(filteredList);
    }

    private class DirVisitor extends Visitor {
        @Override
        protected void whenLeavingDirectory(File file) {

        }

        @Override
        protected void beforeEnteringDirectory(File directory) {

        }

        @Override
        protected void operateWithFile(File current) {
            getListOfFiles().add(current);
        }
    }
}
