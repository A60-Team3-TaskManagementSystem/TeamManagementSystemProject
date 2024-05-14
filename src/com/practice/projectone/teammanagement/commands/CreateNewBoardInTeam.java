package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateNewBoardInTeam extends BaseCommand{

    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    public static final String BOARD_ALREADY_EXISTS = "Board already exists";
    public static final String BOARD_CREATED_SUCCESSFULLY = "%s created successfully in %s";

    public CreateNewBoardInTeam(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);

        return createBoard(boardName,teamName);
    }

    private String createBoard(String boardName, String teamName) {
        Team team = getTeamRepository().findTeamByName(teamName);
        Board board = getTeamRepository().createBoard(boardName);

        if (team.getBoards().contains(board)) {
            throw new DuplicateEntityException(BOARD_ALREADY_EXISTS);
        };

        team.addBoard(board);

        return String.format(BOARD_CREATED_SUCCESSFULLY, boardName, teamName);
    }
}
