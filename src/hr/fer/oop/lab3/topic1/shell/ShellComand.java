package hr.fer.oop.lab3.topic1.shell;

import java.util.Iterator;

/**
 * Created by Luka on 05/12/14.
 */
public interface ShellComand {

    String getCommandName();
    String getCommandDescription();
    CommandStatus execute(Environment, String);


}
