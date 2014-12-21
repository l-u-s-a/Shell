package hr.fer.oop.lab3.topic1.shell.Exceptions;

/**
 * Created by Luka on 21/12/14.
 */
public class KeyNotFoundException extends CommandException {
    public KeyNotFoundException() {
        super("Key not Found");
    }
}
