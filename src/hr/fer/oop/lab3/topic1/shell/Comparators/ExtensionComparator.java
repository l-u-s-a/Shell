package hr.fer.oop.lab3.topic1.shell.Comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Luka on 05/01/15.
 */
public class ExtensionComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        return o1.getName().split(".")[1].compareTo(o2.getName().split(".")[1]);
    }
}
