package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;

import java.util.List;

public class ShowTeams extends BaseCommand {

    private static final String NO_TEAMS = "There are no teams.";

    public ShowTeams(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        return showTeams();
    }

    private String showTeams() {
        StringBuilder builder = new StringBuilder();
        if (getTeamRepository().getTeams().isEmpty()) {
            builder.append(NO_TEAMS);
        } else {
            builder.append("--TEAMS--");
            for (int i = 0; i < getTeamRepository().getTeams().size(); i++) {
                builder.append(System.lineSeparator());
                builder.append(i + 1);
                builder.append(". ");
                builder.append(getTeamRepository().getTeams().get(i).toString());
            }
        }
        return builder.toString();
    }
}
