package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class AssignTaskToMember extends BaseCommand{
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    public static final String TASK_ASSIGNED = "Task with ID%d assigned to %s";


    protected AssignTaskToMember(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), INVALID_TASK_ID);
        String memberName = parameters.get(1);

        return assignTask(taskID, memberName);
    }

    private String assignTask(int taskID, String memberName){
        Task task = getTeamRepository().findTaskByID(taskID);
        Person person = getTeamRepository().findPersonByName(memberName);

        person.assignTask(task);

        return String.format(TASK_ASSIGNED, taskID, memberName);
    }
}
