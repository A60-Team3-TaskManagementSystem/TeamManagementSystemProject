package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class CreateStoryToBoardCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 5;
    private CreateStoryToBoardCommand createStoryToBoardCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        createStoryToBoardCommand = new CreateStoryToBoardCommand(taskManagementSystemRepository);
        validParams = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                Priority.MEDIUM.toString(),
                Size.LARGE.toString(),
                VALID_NAME);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountIsDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createStoryToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_PriorityIsInvalid() {
        List<String> parameters = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                "InvalidPriority",
                Size.LARGE.toString(),
                VALID_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createStoryToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_SizeIsInvalid() {
        List<String> parameters = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                Priority.LOW.toString(),
                "Invalid Size",
                VALID_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createStoryToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardDoesNotExist() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> createStoryToBoardCommand.execute(validParams));
    }

    @Test
    public void execute_Should_AddStoryToBoard_When_ParametersAreValid() {
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        Board board = taskManagementSystemRepository.createBoard(VALID_NAME);

        taskManagementSystemRepository.addTeam(team);
        taskManagementSystemRepository.addBoardToTeam(board, team);


        createStoryToBoardCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, taskManagementSystemRepository.getStories().size()),
                () -> Assertions.assertDoesNotThrow(() -> taskManagementSystemRepository.findStoryByID(1))
        );

    }
}
