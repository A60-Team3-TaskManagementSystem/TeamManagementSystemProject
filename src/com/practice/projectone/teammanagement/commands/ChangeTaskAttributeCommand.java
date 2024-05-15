package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.enums.TaskAttribute;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.models.enums.*;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeTaskAttributeCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 3;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    private static final String INVALID_RATING = "Invalid value for rating. Should be a number.";

    private Task task;

    protected ChangeTaskAttributeCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), INVALID_TASK_ID);
        TaskAttribute taskAttribute = ParsingHelpers.tryParseEnum(parameters.get(1), TaskAttribute.class);
        String newTaskAttributeValue = parameters.get(2);

        this.task = getTeamRepository().findTaskByID(taskID);

        return changeAttribute(taskAttribute, newTaskAttributeValue);
    }

    private String changeAttribute(TaskAttribute taskAttribute, String newTaskAttributeValue) {
        switch (taskAttribute) {
            case SIZE -> {
                Size size = ParsingHelpers.tryParseEnum(newTaskAttributeValue, Size.class);
                changeSize(size);
            }
            case RATING -> {
                int rating = ParsingHelpers.tryParseInt(newTaskAttributeValue, INVALID_RATING);
                changeRating(rating);
            }
            case STATUS -> {
                Status status = ParsingHelpers.tryParseEnum(newTaskAttributeValue, Status.class);
                changeStatus(status);
            }
            case PRIORITY -> {
                Priority priority = ParsingHelpers.tryParseEnum(newTaskAttributeValue, Priority.class);
                changePriority(priority);
            }
            case SEVERITY -> {
                Severity severity = ParsingHelpers.tryParseEnum(newTaskAttributeValue, Severity.class);
                changeSeverity(severity);
            }
        }

        return String.format("Task %s successfully changed to %s", taskAttribute, newTaskAttributeValue);
    }

    private void changeSeverity(Severity severity) {
        task.changeSeverity(severity);
    }

    private void changeStatus(Status status) {
        task.changeStatus(status);
    }

    private void changeSize(Size size) {
        task.changeSize(size);
    }

    private void changePriority(Priority priority) {
        task.changePriority(priority);
    }

    private void changeRating(int rating) {
        task.changeRating(rating);
    }
}
