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
import static com.practice.projectone.teammanagement.utils.Constants.VALID_TITLE;

public class ShowAllTeamBoardsCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private TaskManagementSystemRepository repository;
    private Command showAllTeamBoards;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        showAllTeamBoards = new ShowAllTeamBoardsCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> showAllTeamBoards.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TeamDoesNotExists() {
        Team team = repository.createTeam(VALID_TITLE);
        repository.addTeam(team);

        List<String> notValidParams = List.of(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> showAllTeamBoards.execute(notValidParams));
    }

    @Test
    public void execute_Should_Report_When_NoTeamsInRepo() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showAllTeamBoards.execute(validParams);

        Assertions.assertEquals(1, execute.split(System.lineSeparator()).length);
    }

    @Test
    public void execute_Should_ReturnTeamBoards_When_InputIsValid() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        Board board = repository.createBoard(VALID_NAME);
        team.addBoard(board);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showAllTeamBoards.execute(validParams);

        Assertions.assertEquals(2, execute.split(System.lineSeparator()).length);
    }
}
