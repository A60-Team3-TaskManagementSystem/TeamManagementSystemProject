package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowBoardActivityCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;

    public ShowBoardActivityCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String boardName = parameters.get(0);

        return showBoardActivity(boardName);
    }

    private String showBoardActivity(String boardName) {
        Board board = getTeamRepository().findBoardByName(boardName);

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("--BOARD %s ACTIVITY--", board.getName()));
        board.getActivityHistory().forEach(sb::append);

        return sb.toString().trim();
    }
}
