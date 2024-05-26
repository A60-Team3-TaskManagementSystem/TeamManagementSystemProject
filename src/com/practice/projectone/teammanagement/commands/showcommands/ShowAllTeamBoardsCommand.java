package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowAllTeamBoardsCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private static final String NO_TEAMS = "There are no teams.";
    private static final String NO_BOARDS = "There are no boards.";

    public ShowAllTeamBoardsCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String teamName = parameters.get(0);

        return showAllTeamBoards(teamName);
    }

    private String showAllTeamBoards(String teamName) {
        if (getTMSRepository().getTeams().isEmpty()) return NO_TEAMS;

        Team team = getTMSRepository().findTeamByName(teamName);

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("****TEAM %s****", team.getName()));

        if (team.getBoards().isEmpty()) {
            sb.append(NO_BOARDS);
        }

        for (int i = 0; i < team.getBoards().size(); i++) {
            sb.append(System.lineSeparator());
            sb.append(i + 1);
            sb.append(". ");
            sb.append(team.getBoards().get(i).toString());
        }


        return sb.toString().trim();
    }
}
