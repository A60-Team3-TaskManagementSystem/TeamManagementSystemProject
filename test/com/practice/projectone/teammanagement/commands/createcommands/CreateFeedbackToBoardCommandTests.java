package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class CreateFeedbackToBoardCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 4;
    private CreateFeedbackToBoardCommand createFeedbackToBoardCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        createFeedbackToBoardCommand = new CreateFeedbackToBoardCommand(taskManagementSystemRepository);
        validParams = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                String.valueOf(VALID_RATING),
                VALID_NAME);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountIsDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createFeedbackToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_RatingIsInvalid() {
        List<String> parameters = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                "rating",
                VALID_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createFeedbackToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardDoesNotExist() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> createFeedbackToBoardCommand.execute(validParams));
    }

    @Test
    public void execute_Should_AddFeedbackToBoard_When_ParametersAreValid() {
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        Board board = taskManagementSystemRepository.createBoard(VALID_NAME);

        taskManagementSystemRepository.addTeam(team);
        taskManagementSystemRepository.addBoardToTeam(board, team);


        createFeedbackToBoardCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, taskManagementSystemRepository.getFeedbacks().size()),
                () -> Assertions.assertDoesNotThrow(() -> taskManagementSystemRepository.findFeedbackById(1))
        );

    }
}
