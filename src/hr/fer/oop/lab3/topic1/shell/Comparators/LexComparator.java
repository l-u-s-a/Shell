package hr.fer.oop.lab3.topic1.shell.Comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Luka on 05/01/15.
 */
public class LexComparator implements Comparator<File> {
    @Override
    public int compare(File firstFile, File secondFile) {
        return firstFile.getName().compareTo(secondFile.getName());
    }
}
