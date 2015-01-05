package hr.fer.oop.lab3.topic1.shell.Comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Luka on 05/01/15.
 */
public class TypeComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        if ((o1.isDirectory() && o2.isDirectory()) || (o1.isFile() && o2.isFile())) return 0;
        if (o1.isFile() && o2.isDirectory()) return -1;
        else return 1;
    }
}
