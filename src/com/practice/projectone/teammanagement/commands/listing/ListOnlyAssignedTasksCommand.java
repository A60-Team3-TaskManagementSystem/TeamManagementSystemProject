package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.tasks.contracts.SpecificTask;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListOnlyAssignedTasksCommand extends BaseCommand {

    public static final int OR_FILTER = 2;
    public static final int AND_FILTER = 3;

    public ListOnlyAssignedTasksCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        List<SpecificTask> assignedTasks = getTMSRepository()
                .getSpecificTasks()
                .stream()
                .filter(task -> !task.getAssignee().equals("Not assigned yet"))
                .collect(Collectors.toList());

        if (!parameters.isEmpty()) {
            String toSort = parameters.get(0);

            if (!"true".equals(toSort) && !"false".equals(toSort)){
                throw new IllegalArgumentException("No sorting condition provided");
            }

            if (parameters.size() == OR_FILTER) {
                String filter = parameters.get(1);
                assignedTasks = filterTasks(assignedTasks, filter);
            }

            if (parameters.size() == AND_FILTER) {
                String status = parameters.get(1);
                String assigneeName = parameters.get(2);
                assignedTasks = filterTasks(assignedTasks, status, assigneeName);
            }

            if (Boolean.parseBoolean(toSort)) assignedTasks.sort(Comparator.comparing(Task::getName));
        }

        return getResult(assignedTasks);
    }

    private List<SpecificTask> filterTasks(List<SpecificTask> specificTasks, String... filters) {
        String filter = filters[0];

        Predicate<SpecificTask> filterPredicate = task -> task.getAssignee().contains(filter)
                || task.getStatus().toString().equals(filter);

        if (filters.length == 2) {
            Status status = ParsingHelpers.tryParseEnum(filter, Status.class);
            String assigneeName = filters[1];

            filterPredicate = task -> task.getStatus().equals(status) && task.getAssignee().contains(assigneeName);
        }

        return specificTasks.stream().filter(filterPredicate).collect(Collectors.toList());
    }


    private <T extends Task> String getResult(List<T> tasks) {
        if (tasks.isEmpty()) throw new ElementNotFoundException("No any assigned task");

        return tasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}