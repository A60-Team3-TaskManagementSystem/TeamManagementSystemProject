package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class ShowTeamMembersCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private TaskManagementSystemRepository repository;
    private Command showTeamMembers;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        showTeamMembers = new ShowTeamMembers(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> showTeamMembers.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TeamDoesNotExists() {
        List<String> notValidParams = List.of(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> showTeamMembers.execute(notValidParams));
    }

    @Test
    public void execute_Should_Report_When_TeamsHasNoMembers() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showTeamMembers.execute(validParams);

        Assertions.assertEquals(2, execute.split(System.lineSeparator()).length);
    }

    @Test
    public void execute_Should_ReturnTeamMembers_When_InputIsValid() {
        Team team = repository.createTeam(VALID_NAME);
        repository.addTeam(team);

        Person person = repository.createPerson(VALID_NAME);
        team.addMember(person);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showTeamMembers.execute(validParams);

        Assertions.assertEquals(4, execute.split(System.lineSeparator()).length);
    }
}
