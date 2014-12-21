package hr.fer.oop.lab3.topic1.shell.commands;

import hr.fer.oop.lab3.topic1.shell.CommandStatus;
import hr.fer.oop.lab3.topic1.shell.Environment;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SystemCommand {
    public static CommandStatus execute(Environment environment, String inputArguments) {
        CommandStatus commandStatus = CommandStatus.CONTINUE;
        String[] input = inputArguments.split("[ ]+");
        try {
            Process process =
                    new ProcessBuilder(input)
                    .redirectErrorStream(true)
                    .directory(environment.getActiveTerminal().getCurrentPath().toFile())
                    .start();


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String inputLine = null;
            String outputLine = null;

            while ( (inputLine = bufferedReader.readLine()) != null) environment.writeln(inputLine);

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

//            while ( (outputLine = environment.readLine()) != null) {
//                bufferedWriter.write(outputLine);
//                bufferedWriter.flush();
//            }

            process.waitFor();

        } catch (Exception e) {
            environment.writeln(e.getMessage());
            return CommandStatus.CONTINUE;
        }

        return commandStatus;

    }

    public static boolean exists(String command) {
        return Files.exists(Paths.get("/usr/bin/" + command));
    }
}
