package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class AddTeamMemberCommand extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String ADDED_MEMBER_TO_TEAM = "%s added to team %s successfully!";

    public AddTeamMemberCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String name = parameters.get(0);
        String teamName = parameters.get(1);

        return addTeamMember(name, teamName);
    }

    private String addTeamMember(String name, String teamName) {
        Person person = getTMSRepository().findPersonByName(name);
        Team team = getTMSRepository().findTeamByName(teamName);

        getTMSRepository().addMemberToTeam(person, team);
        return String.format(ADDED_MEMBER_TO_TEAM, person.getName(), team.getName());
    }

}
