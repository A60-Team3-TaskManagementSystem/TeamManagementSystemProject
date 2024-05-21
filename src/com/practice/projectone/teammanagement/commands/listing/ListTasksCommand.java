package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListTasksCommand extends BaseCommand {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 0;
    public static final String LISTING_FACTOR_INVALID = "Listing mechanism incorrect. Please try again";
    private String taskTitleSection;

    public ListTasksCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        List<Task> tasks = getTMSRepository().getTasks();

        if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 1) {

            taskTitleSection = parameters.get(0);

            return filterAndSortTasks(taskTitleSection, tasks);

        } else if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 2) {

            String action = parameters.get(1);
            return filterOrSortTasks(taskTitleSection, action, tasks);
        }

        return listAllTasks(tasks);
    }

    private String filterAndSortTasks(String taskTitle, List<Task> tasks) {
        List<Task> filteredTasks = filterTasks(tasks, taskTitle);
        filteredTasks.sort(Comparator.comparing(Task::getName));

        return listAllTasks(filteredTasks);
    }

    private String filterOrSortTasks(String taskTitle, String action, List<Task> tasks) {

        switch (action) {
            case "sort":
                tasks.sort(Comparator.comparing(Task::getName));
                return listAllTasks(tasks);
            case "filter":
                return listAllTasks(filterTasks(tasks, taskTitle));
            default:
                throw new InvalidUserInputException(LISTING_FACTOR_INVALID);
        }
    }

    private List<Task> filterTasks(List<Task> tasks, String filter) {
        return tasks.stream()
                .filter(task -> task.getName().contains(filter))
                .collect(Collectors.toList());
    }

    private String listAllTasks(List<Task> list) {
        return list
                .stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
