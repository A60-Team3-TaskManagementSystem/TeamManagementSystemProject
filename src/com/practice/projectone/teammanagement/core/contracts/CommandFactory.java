package com.practice.projectone.teammanagement.core.contracts;


import com.practice.projectone.teammanagement.commands.contracts.Command;

public interface CommandFactory {

    Command createCommandFromCommandName(String commandTypeAsString, TaskManagementSystemRepository taskManagementSystemRepository);

}
