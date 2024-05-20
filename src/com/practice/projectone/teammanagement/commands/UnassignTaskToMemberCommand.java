package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.tasks.contracts.SpecificTask;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class UnassignTaskToMemberCommand extends BaseCommand {

    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    public static final String TASK_UNASSIGNED = "Task with ID%d removed from %s";


    public UnassignTaskToMemberCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), INVALID_TASK_ID);
        String memberName = parameters.get(1);

        return removeTask(taskID, memberName);
    }

    private String removeTask(int taskID, String memberName) {
        SpecificTask task = getTMSRepository().findSpecificTask(taskID);
        Person person = getTMSRepository().findPersonByName(memberName);

        person.unassignTask(task);
        task.changeAssignee(null);

        return String.format(TASK_UNASSIGNED, taskID, memberName);
    }
}
