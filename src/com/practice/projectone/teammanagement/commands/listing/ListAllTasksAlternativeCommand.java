package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllTasksAlternativeCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String LISTING_FACTOR_INVALID = "Listing mechanism incorrect. Please try again";
    private String taskTitle;

    public ListAllTasksAlternativeCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 1) {

            taskTitle = parameters.get(0);
            return filterAndSortTasks(taskTitle);

        } else if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 2) {

            String action = parameters.get(1);
            return filterOrSortTasks(taskTitle, action);
        }

        return listAllTask();
    }

    private String listAllTask() {
        return getTeamRepository().getTasks()
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String filterAndSortTasks(String taskTitle) {
        return getTeamRepository()
                .getTasks()
                .stream()
                .filter(task -> task.getName().equals(taskTitle))
                .sorted(Comparator.comparing(Task::getName))
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String filterOrSortTasks(String taskTitle, String action) {
        String result;

        switch (action) {
            case "sort" -> result = getTeamRepository().getTasks()
                    .stream()
                    .sorted(Comparator.comparing(Task::getName))
                    .map(Task::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            case "filter" -> result = getTeamRepository().getTasks()
                    .stream()
                    .filter(task -> task.getName().equals(taskTitle))
                    .map(Task::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            default -> {
                throw new InvalidUserInputException(LISTING_FACTOR_INVALID);
            }
        }

        return result;
    }
}
