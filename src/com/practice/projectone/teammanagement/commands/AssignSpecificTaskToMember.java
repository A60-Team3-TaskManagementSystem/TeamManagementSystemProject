package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;

import java.util.List;

public class AssignSpecificTaskToMember extends BaseCommand{
    protected AssignSpecificTaskToMember(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
