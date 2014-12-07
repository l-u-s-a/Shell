package hr.fer.oop.lab3.topic1.shell;

import java.io.File;

/**
 * Created by Luka on 07/12/14.
 */
public abstract class Visitor {

    protected String inputArguments;
    protected Environment environment;

    protected Visitor(String inputArguments, Environment environment) {
        this.inputArguments = inputArguments;
        this.environment = environment;
    }

    public void Visit(File current){
        if (current.isFile()) {
            operateWithFile(current);
            return;
        }

        if (current.isDirectory()) {

            File[] filesInDirectory = current.listFiles();

            if (filesInDirectory != null) {
                for (File file : filesInDirectory) {
                    beforeEnteringDirectory(file);
                    Visit(file);
                    whenLeavingDirectory(file);
                }
            }
        }
    }


    protected abstract void whenLeavingDirectory(File file);

    protected abstract void beforeEnteringDirectory(File directory);

    protected abstract void operateWithFile(File current);

}
