package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;

public class CreateBugToBoardCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 6;
    private static final String BUG_CREATED_SUCCESSFULLY = "Bug created successfully in %s";

    public CreateBugToBoardCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String title = parameters.get(0);
        String description = parameters.get(1);
        List<String> steps = Arrays.asList(parameters.get(2).split("; "));
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(3), Priority.class);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(4), Severity.class);
        String boardName = parameters.get(5);

        return createBug(title, description, priority, severity, steps, boardName);
    }

    private String createBug(String title, String description, Priority priority,
                             Severity severity, List<String> steps, String boardName) {

        Board board = getTMSRepository().findBoardByName(boardName);
        Bug bug = getTMSRepository().createBug(title, description, priority, severity, steps);
        getTMSRepository().addBug(bug);

        getTMSRepository().addTaskToBoard(board, bug);

        return String.format(BUG_CREATED_SUCCESSFULLY, boardName);
    }
}
