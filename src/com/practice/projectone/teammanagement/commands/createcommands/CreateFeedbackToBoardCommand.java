package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackToBoardCommand extends BaseCommand {
    private static final int EXPECTED_PARAMETERS_COUNT = 4;
    private static final String STORY_CREATED_SUCCESSFULLY = "Story created successfully in %s";
    public static final String INVALID_RATING = "Invalid value for rating. Should be a number.";

    public CreateFeedbackToBoardCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String title = parameters.get(0);
        String description = parameters.get(1);
        int rating = ParsingHelpers.tryParseInt(parameters.get(2), INVALID_RATING);
        String boardName = parameters.get(3);

        return createFeedback(title, description, rating, boardName);
    }

    private String createFeedback(String title, String description, int rating, String boardName) {

        Board board = getTeamRepository().findBoardByName(boardName);
        Feedback feedback = getTeamRepository().createFeedback(title, description, rating);

        getTeamRepository().addTaskToBoard(board, feedback);

        return String.format(STORY_CREATED_SUCCESSFULLY, boardName);
    }
}
