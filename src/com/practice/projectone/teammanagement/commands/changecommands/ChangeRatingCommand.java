package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeRatingCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_VALUE = "Invalid value for %s. Should be a number.";
    private static final String SIZE_CHANGED = "Story size successfully changed to %s";

    public ChangeRatingCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), String.format(INVALID_VALUE, "taskID"));
        int rating = ParsingHelpers.tryParseInt(parameters.get(1), String.format(INVALID_VALUE, "rating"));

        return changeRating(taskID, rating);
    }

    private String changeRating(int taskID, int rating) {

        Feedback feedback = getTMSRepository().findFeedbackById(taskID);
        feedback.changeRating(rating);

        return String.format(SIZE_CHANGED, rating);
    }
}
