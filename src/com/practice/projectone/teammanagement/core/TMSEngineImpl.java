package com.practice.projectone.teammanagement.core;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.contracts.CommandFactory;
import com.practice.projectone.teammanagement.core.contracts.TMSEngine;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TMSEngineImpl implements TMSEngine {

    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND_ERROR = "Command cannot be empty.";
    private static final String MAIN_SPLIT_SYMBOL = " ";
    private static final String LONG_INPUT_OPEN_SYMBOL = "{{";
    private static final String LONG_INPUT_CLOSE_SYMBOL = "}}";
    private static final String REPORT_SEPARATOR = "####################";
    private final CommandFactory commandFactory;
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    public TMSEngineImpl() {
        this.commandFactory = new CommandFactoryImpl();
        this.taskManagementSystemRepository = new TaskManagementSystemImpl();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    print(EMPTY_COMMAND_ERROR);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    print(ex.getMessage());
                } else {
                    print(ex.toString());
                }
            }
        }
    }

    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        List<String> parameters = extractCommandParameters(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName, taskManagementSystemRepository);
        String executionResult = command.execute(parameters);
        print(executionResult);
    }

    /**
     * Receives a full line and extracts the command to be executed from it.
     * For example, if the input line is "FilterBy Assignee John", the method will return "FilterBy".
     *
     * @param inputLine A complete input line
     * @return The name of the command to be executed
     */
    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }

    /**
     * Receives a full line and extracts the parameters that are needed for the command to execute.
     * For example, if the input line is "FilterBy Assignee John",
     * the method will return a list of ["Assignee", "John"].
     *
     * @param inputLine A complete input line
     * @return A list of the parameters needed to execute the command
     */
    private List<String> extractCommandParameters(String inputLine) {
        if (inputLine.contains(LONG_INPUT_OPEN_SYMBOL)) {
            return extractCommentParameters(inputLine);
        }
        String[] commandParts = inputLine.split(" ");
        List<String> parameters = new ArrayList<>();
        for (int i = 1; i < commandParts.length; i++) {
            parameters.add(commandParts[i]);
        }
        return parameters;
    }

    public List<String> extractCommentParameters(String fullCommand) {
        int indexOfFirstSeparator = fullCommand.indexOf(MAIN_SPLIT_SYMBOL);
        int indexOfOpenComment = fullCommand.indexOf(LONG_INPUT_OPEN_SYMBOL);
        int indexOfCloseComment = fullCommand.indexOf(LONG_INPUT_CLOSE_SYMBOL);

        List<String> parameters = new ArrayList<>();

        while (indexOfOpenComment != -1) {
            String parameter = fullCommand
                    .substring(indexOfOpenComment + LONG_INPUT_OPEN_SYMBOL.length(), indexOfCloseComment);
            parameters.add(parameter);

            fullCommand = fullCommand
                    .replace(String.format("%s%s%s", LONG_INPUT_OPEN_SYMBOL, parameter, LONG_INPUT_CLOSE_SYMBOL), "");

            indexOfOpenComment = fullCommand.indexOf(LONG_INPUT_OPEN_SYMBOL);
            indexOfCloseComment = fullCommand.indexOf(LONG_INPUT_CLOSE_SYMBOL);
        }

        List<String> result = new ArrayList<>(Arrays.asList(fullCommand.substring(indexOfFirstSeparator + 1).split(MAIN_SPLIT_SYMBOL)));
        result.removeAll(Arrays.asList(" ", "", null));
        parameters.addAll(result);
        return parameters;
    }

    private void print(String result) {
        System.out.println(result);
        System.out.println(REPORT_SEPARATOR);
    }
}
