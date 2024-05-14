package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreatePerson extends BaseCommand{

    private final static String PERSON_CREATED = "Person %s created successfully!";
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public CreatePerson(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String name = parameters.get(0);

        return createPerson(name);
    }

    private String createPerson(String name){
        Person person = getTeamRepository().createPerson(name);
        getTeamRepository().addPerson(person);

        return String.format(PERSON_CREATED, name);
    }
}
