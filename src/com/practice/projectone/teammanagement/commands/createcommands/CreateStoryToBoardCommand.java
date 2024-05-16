package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.commands.BaseCommand;
import com.practice.projectone.teammanagement.core.contracts.TeamRepository;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.utils.ParsingHelpers;
import com.practice.projectone.teammanagement.utils.ValidationHelpers;

import java.util.List;

public class CreateStoryToBoardCommand extends BaseCommand {

    private static final int EXPECTED_PARAMETERS_COUNT = 5;
    private static final String STORY_CREATED_SUCCESSFULLY = "Story created successfully in %s";

    public CreateStoryToBoardCommand(TeamRepository teamRepository) {
        super(teamRepository);
    }

    @Override
    public String execute(List<String> parameters) {
        ValidationHelpers.validateArgumentsCount(parameters, EXPECTED_PARAMETERS_COUNT);

        String title = parameters.get(0);
        String description = parameters.get(1);
        Priority priority = ParsingHelpers.tryParseEnum(parameters.get(2), Priority.class);
        Size size = ParsingHelpers.tryParseEnum(parameters.get(3), Size.class);
        String boardName = parameters.get(4);

        return createStory(title, description, priority, size, boardName);
    }

    private String createStory(String title, String description, Priority priority,
                               Size size, String boardName) {

        Board board = getTeamRepository().findBoardByName(boardName);
        Story story = getTeamRepository().createStory(title, description, priority, size);

        getTeamRepository().addTaskToBoard(board, story);

        return String.format(STORY_CREATED_SUCCESSFULLY, boardName);
    }
}
