package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.FeedbackImpl;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateFeedbackCommand extends BaseCommand{
    private static final int EXPECTED_PARAMETERS_COUNT = 4;
    private static final String STORY_CREATED_SUCCESSFULLY = "Story created successfully in %s";
    public static final String INVALID_RATING = "Invalid value for rating. Should be a number.";


    public CreateFeedbackCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String title = parameters.get(0);
        String description = parameters.get(1);
        double rating = ParsingHelpers.tryParseDouble(parameters.get(2), INVALID_RATING);
        String boardName = parameters.get(3);


        return createFeedback(title, description, rating, boardName);
    }

    private String createFeedback(String title, String description, double rating, String boardName){
        Task feedback = new FeedbackImpl(title, description,rating);

        Board board = getTeamRepository().findBoardByName(boardName);

        getTeamRepository().createTask(board, feedback);

        return String.format(STORY_CREATED_SUCCESSFULLY, boardName);
    }
}
