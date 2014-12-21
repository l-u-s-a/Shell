package hr.fer.oop.lab3.topic1.shell.Exceptions;

/**
 * Created by Luka on 07/12/14.
 */
public class CommandException extends RuntimeException {
    public CommandException(String message) {
        super(message);
    }
}
