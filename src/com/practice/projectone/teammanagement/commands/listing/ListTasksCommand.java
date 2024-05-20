package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListAllTasksAlternativeCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String LISTING_FACTOR_INVALID = "Listing mechanism incorrect. Please try again";
    private String taskTitleSection;

    public ListAllTasksAlternativeCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 1) {

            taskTitleSection = parameters.get(0);
            return filterAndSortTasks(taskTitleSection);

        } else if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 2) {

            String action = parameters.get(1);
            return filterOrSortTasks(taskTitleSection, action);
        }

        return listAllTask();
    }

    private String listAllTask() {
        return getTMSRepository().getTasks()
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String filterAndSortTasks(String taskTitle) {
        return getTMSRepository()
                .getTasks()
                .stream()
                .filter(task -> task.getName().contains(taskTitle))
                .sorted(Comparator.comparing(Task::getName))
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String filterOrSortTasks(String taskTitle, String action) {
        String result;

        switch (action) {
            case "sort" -> result = getTMSRepository().getTasks()
                    .stream()
                    .sorted(Comparator.comparing(Task::getName))
                    .map(Task::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            case "filter" -> result = getTMSRepository()
                    .getTasks()
                    .stream()
                    .filter(task -> task.getName().contains(taskTitle))
                    .map(Task::toString)
                    .collect(Collectors.joining(System.lineSeparator()));
            default -> {
                throw new InvalidUserInputException(LISTING_FACTOR_INVALID);
            }
        }

        return result;
    }
}
