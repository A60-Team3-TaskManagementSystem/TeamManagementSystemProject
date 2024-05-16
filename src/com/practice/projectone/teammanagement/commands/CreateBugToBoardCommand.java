package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Bug;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Severity;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.Arrays;
import java.util.List;

public class CreateBugToBoardCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 7;
    private static final String BUG_CREATED_SUCCESSFULLY = "Bug created successfully in %s";


    public CreateBugToBoardCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        List<String> steps = Arrays.asList(parameters.get(0).split("; "));
        String title = parameters.get(1);
        String description = parameters.get(2);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(3), Priority.class);
        Severity severity = ParsingHelpers.tryParseEnum(parameters.get(4), Severity.class);
        String assigneeName = parameters.get(5);
        String boardName = parameters.get(6);


        return createBug(title, description, priority, severity, assigneeName, steps, boardName);
    }

    private String createBug(String title, String description, Priority priority,
                             Severity severity, String assigneeName, List<String> steps, String boardName) {

        Board board = getTeamRepository().findBoardByName(boardName);
        Bug bug = getTeamRepository().createBug(title, description, priority, severity, assigneeName, steps);

        getTeamRepository().addTaskToBoard(board, bug);

        return String.format(BUG_CREATED_SUCCESSFULLY, boardName);
    }
}
