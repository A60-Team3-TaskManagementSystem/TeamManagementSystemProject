package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;

import java.util.List;

public class ShowPeople extends BaseCommand {

    private static final String NO_PEOPLE = "There are no people.";

    public ShowPeople(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        return showPeople();
    }

    private String showPeople() {
        StringBuilder builder = new StringBuilder();
        if (getTeamRepository().getMembers().isEmpty()) {
            builder.append(NO_PEOPLE);
        } else {
            builder.append("--PEOPLE--");
            for (int i = 0; i < getTeamRepository().getMembers().size(); i++) {
                builder.append(System.lineSeparator());
                builder.append(i + 1);
                builder.append(". ");
                builder.append(getTeamRepository().getMembers().get(i).toString());
            }
        }
        return builder.toString();
    }
}
