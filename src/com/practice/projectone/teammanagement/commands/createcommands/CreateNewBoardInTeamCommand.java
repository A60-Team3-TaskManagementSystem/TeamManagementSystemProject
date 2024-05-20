package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateNewBoardInTeamCommand extends BaseCommand {

    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String BOARD_CREATED_SUCCESSFULLY = "%s created successfully in %s";

    public CreateNewBoardInTeamCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);

        return createBoard(boardName, teamName);
    }

    private String createBoard(String boardName, String teamName) {

        getTMSRepository().createBoard(teamName, boardName);

        return String.format(BOARD_CREATED_SUCCESSFULLY, boardName, teamName);
    }
}
