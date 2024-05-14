package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.contracts.TMSEngine;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;

import java.util.List;

public abstract class BaseCommand implements Command {
    private final TeamRepository teamRepository;

    protected BaseCommand(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    protected TeamRepository getTeamRepository() {
        return teamRepository;
    }
}
