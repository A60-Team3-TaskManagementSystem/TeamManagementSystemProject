package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateTeamCommand extends BaseCommand {

    private final static String TEAM_CREATED = "Team %s created successfully!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public CreateTeamCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(0);

        return createTeam(teamName);
    }

    private String createTeam(String teamName) {
        Team team = getTeamRepository().createTeam(teamName);
        getTeamRepository().addTeam(team);

        return String.format(TEAM_CREATED, teamName);
    }
}
