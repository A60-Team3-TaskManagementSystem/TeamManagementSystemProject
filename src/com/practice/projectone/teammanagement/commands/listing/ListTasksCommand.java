package com.practice.projectone.teammanagement.commands.listing;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Comparator;
import java.util.List;

public class ListTasksCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    public ListTasksCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String sort = parameters.get(0);
        String filter = parameters.get(1);
        return listTasks(sort, filter);
    }

    private String listTasks(String sort, String filter) {
        StringBuilder builder = new StringBuilder();
        List<Team> teams = getTMSRepository().getTeams();
        List<Board> boards = teams.stream()
                .flatMap(team -> team.getBoards().stream())
                .toList();
        if (sort.equalsIgnoreCase("nosort") && filter.equalsIgnoreCase("nofilter")) {
            boards
                    .stream()
                    .flatMap(board -> board.getTasks().stream())
                    .forEach(builder::append);
        } else if (sort.equalsIgnoreCase("title") && filter.equalsIgnoreCase("nofilter")) {
            boards
                    .stream()
                    .flatMap(board -> board.getTasks().stream())
                    .sorted(Comparator.comparing(Task::getName))
                    .forEach(builder::append);
        } else if (sort.equalsIgnoreCase("title") && !filter.equalsIgnoreCase("nofilter")) {
            boards
                    .stream()
                    .flatMap(board -> board.getTasks().stream())
                    .filter(task -> task.getName().equals(filter))
                    .sorted(Comparator.comparing(Task::getName))
                    .forEach(builder::append);
        } else if (sort.equalsIgnoreCase("nosort") && !filter.equalsIgnoreCase("nofilter")) {
            boards
                    .stream()
                    .flatMap(board -> board.getTasks().stream())
                    .filter(task -> task.getName().equals(filter))
                    .forEach(builder::append);
        } else {
            throw new IllegalArgumentException("Invalid sorting parameter: should be \"title\" or \"nosort\"");
        }
        return builder.toString();
    }
}
