package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Team;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class CreateTeamTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private CreateTeamCommand createTeamCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        createTeamCommand = new CreateTeamCommand(taskManagementSystemRepository);
        validParams = List.of(VALID_NAME);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountIsDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createTeamCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_PersonAlreadyExist() {
        createTeamCommand.execute(validParams);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createTeamCommand.execute(validParams));
    }

    @Test
    public void execute_Should_CreatePerson_When_ParametersAreValid() {
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);

        createTeamCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertTrue(taskManagementSystemRepository.getTeams().contains(team)),
                () -> Assertions.assertEquals(team, taskManagementSystemRepository.findTeamByName(team.getName()))
        );
    }
}
