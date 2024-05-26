package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
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

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class ShowBoardActivityCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private TaskManagementSystemRepository repository;
    private Command showBoardActivity;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        showBoardActivity = new ShowBoardActivityCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> showBoardActivity.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_BoardDoesNotExists() {
        List<String> notValidParams = List.of(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> showBoardActivity.execute(notValidParams));
    }

    @Test
    public void execute_Should_ReturnBoardActivity_When_InputIsValid() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        Board board = repository.createBoard(VALID_NAME);
        team.addBoard(board);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showBoardActivity.execute(validParams);

        Assertions.assertEquals(4, execute.split(System.lineSeparator()).length);
    }
}
