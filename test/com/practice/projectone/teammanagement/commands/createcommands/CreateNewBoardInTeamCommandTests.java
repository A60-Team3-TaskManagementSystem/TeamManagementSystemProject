package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.DuplicateEntityException;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class CreateNewBoardInTeamCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private CreateNewBoardInTeamCommand createNewBoardInTeamCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        createNewBoardInTeamCommand = new CreateNewBoardInTeamCommand(taskManagementSystemRepository);
        validParams = List.of(VALID_NAME, VALID_NAME);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountIsDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createNewBoardInTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamDoesNotExist() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> createNewBoardInTeamCommand.execute(validParams));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardAlreadyExistInTeam() {
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        Board board = taskManagementSystemRepository.createBoard(VALID_NAME);

        taskManagementSystemRepository.addTeam(team);
        taskManagementSystemRepository.addBoardToTeam(board, team);

        Assertions.assertThrows(DuplicateEntityException.class, () -> createNewBoardInTeamCommand.execute(validParams));
    }

    @Test
    public void execute_Should_AddBoardToTeam_When_ParametersAreValid() {
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        Board board = taskManagementSystemRepository.createBoard(VALID_NAME);

        taskManagementSystemRepository.addTeam(team);

        createNewBoardInTeamCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertTrue(team.getBoards().contains(board)),
                () -> Assertions.assertEquals(board, taskManagementSystemRepository.findBoardByName(board.getName()))
        );
    }
}
