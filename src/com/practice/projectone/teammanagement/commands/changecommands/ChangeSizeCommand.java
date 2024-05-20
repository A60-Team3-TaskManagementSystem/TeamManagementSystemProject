package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class ChangeSizeCommand extends BaseCommand {

    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    private static final String SIZE_CHANGED = "Story size successfully changed to %s";

    public ChangeSizeCommand(TaskManagementSystemRepository taskManagementSystemRepository) {
        super(taskManagementSystemRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), INVALID_TASK_ID);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(1), Size.class);

        return changeSeverity(taskID, size);
    }

    private String changeSeverity(int taskID, Size size) {

        Story story = getTMSRepository().findStoryByID(taskID);
        story.changeSize(size);

        return String.format(SIZE_CHANGED, size);
    }
}
