package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.EventLog;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowPersonActivityLog extends BaseCommand{

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowPersonActivityLog(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String name = parameters.get(0);
        return showPersonActivityLog(name);
    }

    private String showPersonActivityLog(String name) {

        Person person = getTeamRepository().findPersonByName(name);
        StringBuilder builder = new StringBuilder();
        for(EventLog event : person.getActivityHistory()){
            builder.append(event).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
