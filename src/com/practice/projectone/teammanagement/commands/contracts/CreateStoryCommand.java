package com.practice.projectone.teammanagement.commands.contracts;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.StoryImpl;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Task;
import com.practice.projectone.teammanagement.models.enums.Priority;
import com.practice.projectone.teammanagement.models.enums.Size;
import com.practice.projectone.teammanagement.models.enums.Status;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateStory extends BaseCommand {

    private static final int EXPECTED_PARAMETERS_COUNT = 6;
    private static final String STORY_CREATED_SUCCESSFULLY = "Story created successfully in %s";

    public CreateStory(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(2), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(3), Size.class);
        String assigneeName = parameters.get(4);
        String boardName = parameters.get(5);



        return createStory(title, description, priority, size, assigneeName, boardName);
    }

    private String createStory(String title, String description, Priority priority,
                               Size size, String assigneeName, String boardName) {

        Task story = new StoryImpl(title, description, priority, size, assigneeName);
        Board board = getTeamRepository().findBoardByName(boardName);

        getTeamRepository().createTask(board, story);

        return String.format(STORY_CREATED_SUCCESSFULLY, boardName);
    }
}
