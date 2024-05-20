package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public class ShowTeams extends BaseCommand {

    private static final String NO_TEAMS = "There are no teams.";

    public ShowTeams(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        return showTeams();
    }

    private String showTeams() {
        StringBuilder builder = new StringBuilder();
        if (getTMSRepository().getTeams().isEmpty()) {
            builder.append(NO_TEAMS);
        } else {
            builder.append("--TEAMS--");
            for (int i = 0; i < getTMSRepository().getTeams().size(); i++) {
                builder.append(System.lineSeparator());
                builder.append(i + 1);
                builder.append(". ");
                builder.append(getTMSRepository().getTeams().get(i).toString());
            }
        }
        return builder.toString();
    }
}
