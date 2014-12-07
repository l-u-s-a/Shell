package hr.fer.oop.lab3.topic1.shell;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by Luka on 05/12/14.
 */
public class Terminal {
    private Path currentPath;
    private int terminalNumber;

    public Terminal(int terminalNumber) {
        this.terminalNumber = terminalNumber;
        currentPath = Paths.get("").toAbsolutePath();
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    public int getTerminalNumber() {
        return terminalNumber;
    }

    public void setCurrentPath(Path currentPath) {
        this.currentPath = currentPath.toAbsolutePath();
//        capitalizePathFolders();
    }

    private void capitalizePathFolders() {
        String separator = File.separator;
        String[] pathStrings = currentPath.toString().split(separator);
        StringBuilder capitalizedBuilder = new StringBuilder();
        for (int i = 1; i < pathStrings.length; i++) {
            String currentString = pathStrings[i];
            String capitalizedString = currentString.substring(0, 1).toUpperCase() + currentString.substring(1);
            capitalizedBuilder.append(capitalizedString);

            if (i < pathStrings.length - 1) capitalizedBuilder.append(separator);
        }
        currentPath = Paths.get(capitalizedBuilder.toString());
    }


    public String getPrompt(){
        StringBuilder prompt = new StringBuilder();
        prompt.append(terminalNumber).append("$").append(currentPath).append("> ");
        return prompt.toString();
    }
}
