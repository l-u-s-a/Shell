package hr.fer.oop.lab3.topic1.shell.Comparators;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Luka on 05/01/15.
 */
public class ExtensionComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {

        if (!o1.getName().contains(".") && !o2.getName().contains(".")) return 0;

        if (!o1.getName().contains(".") && o2.getName().contains(".")) return -1;

        if (o1.getName().contains(".") && !o2.getName().contains(".")) return 1;

        String firstExtension = o1.getName().split("\\.")[1];
        String secondExtension = o2.getName().split("\\.")[1];

        return firstExtension.compareTo(secondExtension);
    }
}
