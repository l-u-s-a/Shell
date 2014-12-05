package hr.fer.oop.lab3.topic1.shell;

import java.util.Iterator;

/**
 * Created by Luka on 05/12/14.
 */
public class Terminal implements Iterable<ShellComand> {
    private static int numberOfTerminals = 0;
    private int terminalNumber;

    public Terminal() {
        Terminal(1);
    }

    public Terminal(int terminalNumber) {
        numberOfTerminals++;
        this.terminalNumber = terminalNumber;
    }

    @Override
    public Iterator<ShellComand> iterator() {
        return null;
    }
}
