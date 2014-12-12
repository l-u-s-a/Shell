package hr.fer.oop.lab3.topic1.shell.Visitors;

import java.io.File;

/**
 * Created by Luka on 07/12/14.
 */
public abstract class Visitor {

    public void visit(File current){
        if (current.isFile()) {
            operateWithFile(current);
            return;
        }

        if (current.isDirectory()) {

            beforeEnteringDirectory(current);

            File[] filesInDirectory = current.listFiles();

            if (filesInDirectory != null) {
                for (File file : filesInDirectory)
                    visit(file);
            }

            whenLeavingDirectory(current);

        }
    }


    protected abstract void whenLeavingDirectory(File file);

    protected abstract void beforeEnteringDirectory(File directory);

    protected abstract void operateWithFile(File current);

}
