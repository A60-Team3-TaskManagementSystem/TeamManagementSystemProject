package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowTeamMembers extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String TEAM_HAS_NO_MEMBERS = "This team has no members!";

    public ShowTeamMembers(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String teamName = parameters.get(0);

        return showTeamMembers(teamName);
    }

    private String showTeamMembers(String teamName) {
        Team team = getTMSRepository().findTeamByName(teamName);
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("****TEAM %s****", teamName)).append(System.lineSeparator());

        if (team.getMembers().isEmpty()) {
            builder.append(TEAM_HAS_NO_MEMBERS);
        } else {
            builder.append("--MEMBERS--");
            for (int i = 0; i < team.getMembers().size(); i++) {
                builder.append(System.lineSeparator());
                builder.append(i + 1);
                builder.append(". ");
                builder.append(team.getMembers().get(i).toString());
            }
            builder.append(System.lineSeparator()).append("--MEMBERS--");
        }
        return builder.toString();
    }
}
