package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;
import hr.fer.oop.lab3.topic1.shell.Terminal;
import hr.fer.oop.lab3.topic1.shell.commands.AbstractCommand;

/**
 * Created by Luka on 06/12/14.
 */
public class TerminalCommand extends AbstractCommand {

    public TerminalCommand() {
        super("terminal", "Activate existing terminal(if doesn't exist creates one)");
    }

    @Override
    public CommandStatus execute(Environment environment, String inputArguments) {
        int terminalNumber = Integer.parseInt(inputArguments);
        Terminal terminal = environment.getOrCreateTerminal(terminalNumber);
        environment.setActiveTerminal(terminal);

        return CommandStatus.CONTINUE;
    }
}

