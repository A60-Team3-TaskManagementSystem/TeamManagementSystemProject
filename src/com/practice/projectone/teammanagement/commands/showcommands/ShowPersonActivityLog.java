package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ShowPersonActivityLog extends BaseCommand {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    public ShowPersonActivityLog(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        String name = parameters.get(0);
        return showPersonActivityLog(name);
    }

    private String showPersonActivityLog(String name) {
        return getTMSRepository().findPersonByName(name).viewActivity();
    }
}
