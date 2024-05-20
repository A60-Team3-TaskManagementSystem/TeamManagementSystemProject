package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;

public abstract class BaseCommand implements Command {
    private final TaskManagementSystemRepository taskManagementSystemRepository;

    protected BaseCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        this.taskManagementSystemRepository = taskManagementSystemRepository;
    }

    protected TaskManagementSystemRepository getTeamRepository() {
        return taskManagementSystemRepository;
    }
}
