package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Board;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class ShowTeamActivityCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private TaskManagementSystemRepository repository;
    private Command showTeamActivity;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        showTeamActivity = new ShowTeamActivityCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> showTeamActivity.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TeamDoesNotExists() {
        List<String> notValidParams = List.of(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> showTeamActivity.execute(notValidParams));
    }

    @Test
    public void execute_Should_ReturnTeamActivity_When_TeamIsEmpty() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);
        List<String> validParams = List.of(VALID_NAME);

        String execute = showTeamActivity.execute(validParams);

        Assertions.assertEquals(2, execute.split(System.lineSeparator()).length);
    }

    @Test
    public void execute_Should_ReturnTeamActivity_When_TeamHasNoMembers() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        Board board = repository.createBoard(VALID_NAME);
        team.addBoard(board);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showTeamActivity.execute(validParams);

        Assertions.assertEquals(7, execute.split(System.lineSeparator()).length);
    }

    @Test
    public void execute_Should_ReturnTeamActivity_When_TeamHasNoBoards() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        Person person = repository.createPerson(VALID_NAME);
        team.addMember(person);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showTeamActivity.execute(validParams);

        Assertions.assertEquals(7, execute.split(System.lineSeparator()).length);
    }

    @Test
    public void execute_Should_ReturnTeamActivity_When_TeamHasBoardsAndMembers() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        Board board = repository.createBoard(VALID_NAME);
        team.addBoard(board);

        Person person = repository.createPerson(VALID_NAME);
        team.addMember(person);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showTeamActivity.execute(validParams);

        Assertions.assertEquals(10, execute.split(System.lineSeparator()).length);
    }
}
