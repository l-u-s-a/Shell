package hr.fer.oop.lab3.topic1.shell;


/**
 * Created by Luka on 05/12/14.
 */
public interface Environment {
    String readLine();
    void write();
    void writeln();
    Terminal getActiveTerminal();
    void setActiveTerminal(Terminal terminal);
    Terminal getOrCreateTerminal(int terminalNumber);
    Terminal[] listTerminals();
    Iterable commands();
}
