package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TMSEngine;

import java.util.List;

public class AddBoardToTeam extends BaseCommand{

    protected AddBoardToTeam(TMSEngine tmsEngine) {
        super(tmsEngine);
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
