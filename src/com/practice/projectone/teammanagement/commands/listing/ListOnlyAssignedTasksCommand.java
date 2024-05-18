package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.SpecificTask;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListOnlyAssignedTasksCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ListOnlyAssignedTasksCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        String filter = parameters.get(0);
        List<SpecificTask> assignedTasks = getTeamRepository()
                .getSpecificTasks()
                .stream()
                .filter(task -> task.getAssignee() != null)
                .collect(Collectors.toList());

        if (parameters.size() == EXPECTED_NUMBER_OF_ARGUMENTS + 1) {

            String secondFilter = parameters.get(1);

            return listTasks(assignedTasks, filter, secondFilter);
        }

        return listTasks(assignedTasks, filter);
    }

    private String listTasks(List<SpecificTask> specificTasks, String filter) {
        List<String> result = specificTasks.stream()
                .filter(task -> task.getAssignee().equals(filter) || task.getStatus().toString().equals(filter))
                .map(Task::toString)
                .collect(Collectors.toList());

        return String.join(System.lineSeparator(), result).trim();
    }

    private String listTasks(List<SpecificTask> specificTasks, String status, String assigneeName) {
        List<String> result = specificTasks
                .stream()
                .filter(task -> task.getStatus().toString().equals(status))
                .filter(task -> task.getAssignee().equals(assigneeName))
                .sorted(Comparator.comparing(Task::getName))
                .map(Task::toString)
                .collect(Collectors.toList());

        return String.join(System.lineSeparator(), result).trim();
    }
}
