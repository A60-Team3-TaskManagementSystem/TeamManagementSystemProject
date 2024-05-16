package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class AddCommentToTaskCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 3;
    private static final String INVALID_TASK_ID = "Invalid value for taskID. Should be a number.";
    private static final String COMMENT_ADDED = "Comment successfully added to task with ID %d";

    public AddCommentToTaskCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        int taskID = ParsingHelpers.tryParseInt(parameters.get(0), INVALID_TASK_ID);
        String author = parameters.get(1);
        String description = parameters.get(2);


        return addCommentToTask(author, description, taskID);
    }

    private String addCommentToTask(String author, String description, int taskID) {
        Task task = getTeamRepository().findTaskByID(taskID);
        Comment comment = getTeamRepository().createComment(author, description);

        task.addComment(comment);
        return String.format(COMMENT_ADDED, taskID);
    }
}
