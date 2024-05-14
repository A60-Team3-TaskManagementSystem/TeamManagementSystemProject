package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class AddBoardToTeam extends BaseCommand{

    private static final int EXPECTED_PARAMETERS_COUNT = 2;

    public AddBoardToTeam(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String boardName = parameters.get(0);
        String teamName = parameters.get(1);

        return addBoardToTeam(boardName,teamName);
    }

    private String addBoardToTeam(String boardName, String teamName) {

        return null;
    }
}
