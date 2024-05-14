package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.contracts.TMSEngine;

import java.util.List;

public abstract class BaseCommand implements Command {
    private final TMSEngine tmsEngine;

    protected BaseCommand(TMSEngine tmsEngine) {
        this.tmsEngine = tmsEngine;
    }

    protected TMSEngine getTmsEngine() {
        return tmsEngine;
    }
}
