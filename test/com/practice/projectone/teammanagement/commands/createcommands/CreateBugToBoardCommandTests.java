package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class CreateBugToBoardCommandTests {

    private static final int EXPECTED_PARAMETERS_COUNT = 6;
    public static final String STEPS_PARAMETER = "{{step1; step2}}";
    private CreateBugToBoardCommand createBugToBoardCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        createBugToBoardCommand = new CreateBugToBoardCommand(taskManagementSystemRepository);
        validParams = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                STEPS_PARAMETER,
                Priority.MEDIUM.toString(),
                Severity.CRITICAL.toString(),
                VALID_NAME);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountIsDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createBugToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_PriorityIsInvalid() {
        List<String> parameters = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                STEPS_PARAMETER,
                "InvalidPriority",
                Severity.MAJOR.toString(),
                VALID_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createBugToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_SeverityIsInvalid() {
        List<String> parameters = List.of(
                VALID_TITLE,
                VALID_DESCRIPTION,
                STEPS_PARAMETER,
                Priority.MEDIUM.toString(),
                "InvalidSeverity",
                VALID_NAME);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createBugToBoardCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardDoesNotExist() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> createBugToBoardCommand.execute(validParams));
    }

    @Test
    public void execute_Should_AddBugToBoard_When_ParametersAreValid() {
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        Board board = taskManagementSystemRepository.createBoard(VALID_NAME);

        taskManagementSystemRepository.addTeam(team);
        taskManagementSystemRepository.addBoardToTeam(board, team);

        createBugToBoardCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, taskManagementSystemRepository.getBugs().size()),
                () -> Assertions.assertEquals(1, taskManagementSystemRepository
                        .findBoardByName(board.getName()).getTasks().size())
        );

    }
}
