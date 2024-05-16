package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.PrioritizeAble;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangePriorityCommand extends BaseCommand {

    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    private static final String PRIORITY_CHANGED = "Task priority successfully changed to %s";

    public ChangePriorityCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), INVALID_TASK_ID);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(1), Priority.class);

        return changePriority(taskID, priority);
    }

    private String changePriority(int taskID, Priority priority) {

        PrioritizeAble task = getTeamRepository().findPriorityAbleById(taskID);
        task.changePriority(priority);

        return String.format(PRIORITY_CHANGED, priority);
    }
}
