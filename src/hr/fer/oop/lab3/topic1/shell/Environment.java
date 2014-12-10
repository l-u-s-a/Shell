package hr.fer.oop.lab3.topic1.shell;


import hr.fer.oop.lab3.topic1.SimpleHashtable;

/**
 * Created by Luka on 05/12/14.
 */
public interface Environment {
    String readLine();
    void write(String text);
    void writeln(String text);
    Terminal getActiveTerminal();
    void setActiveTerminal(Terminal terminal);
    Terminal getOrCreateTerminal(int terminalNumber);
    Terminal[] listTerminals();
    Iterable<ShellCommand> commands();
}
