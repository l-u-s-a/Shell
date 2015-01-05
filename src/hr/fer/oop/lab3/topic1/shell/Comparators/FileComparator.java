package hr.fer.oop.lab3.topic1.shell.Comparators;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Luka on 05/01/15.
 */
public class FileComparator implements Comparator<File> {
    private List<Comparator<File>> comparators = new ArrayList<>();

    public FileComparator(String argument) {
        for (String character : argument.split("")) {
            System.out.println(character);
            switch (character) {
                case "e":
                    comparators.add(new ExtensionComparator());
                    break;
                case "E":
                    comparators.add(new ExtensionComparator().reversed());
                    break;
                case "s":
                    comparators.add(new SizeComparator());
                    break;
                case "S":
                    comparators.add(new SizeComparator().reversed());
                    break;
                case "D":
                    comparators.add(new DateComparator());
                    break;
                case "d":
                    comparators.add(new DateComparator().reversed());
                    break;
                case "n":
                    comparators.add(new LexComparator());
                    break;
                case "N":
                    comparators.add(new LexComparator().reversed());
                    break;
                case "t":
                    comparators.add(new TypeComparator());
                    break;
                case "T":
                    comparators.add(new TypeComparator().reversed());
                    break;
            }
        }
    }

    @Override
    public int compare(File o1, File o2) {
        for (Comparator<File> comparator : comparators) {
            int result = comparator.compare(o1, o2);
            if (result != 0) return result;
        }
        return 0;
    }
}
