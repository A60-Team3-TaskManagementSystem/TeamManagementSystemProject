package com.practice.projectone.teammanagement.commands;

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

public class AddTeamMemberCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    private Command addTeamMemberCommand;
    private List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        validParams = List.of(VALID_NAME, VALID_NAME);
        addTeamMemberCommand = new AddTeamMemberCommand(taskManagementSystemRepository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> addTeamMemberCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_PersonDoesNotExist() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> addTeamMemberCommand.execute(validParams));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamDoesNotExist() {
        Person person = taskManagementSystemRepository.createPerson(VALID_NAME);
        taskManagementSystemRepository.addPerson(person);

        Assertions.assertThrows(ElementNotFoundException.class, () -> addTeamMemberCommand.execute(validParams));
    }

    @Test
    public void execute_Should_ThrowException_When_MemberAlreadyInTeam() {
        Person person = taskManagementSystemRepository.createPerson(VALID_NAME);
        taskManagementSystemRepository.addPerson(person);
        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        taskManagementSystemRepository.addTeam(team);

        team.addMember(person);

        Assertions.assertThrows(IllegalArgumentException.class, () -> addTeamMemberCommand.execute(validParams));
    }

    @Test
    public void execute_Should_AddMemberToTeam_When_ParametersAreValid() {
        Person person = taskManagementSystemRepository.createPerson(VALID_NAME);
        taskManagementSystemRepository.addPerson(person);

        Team team = taskManagementSystemRepository.createTeam(VALID_NAME);
        taskManagementSystemRepository.addTeam(team);

        addTeamMemberCommand.execute(validParams);

        Assertions.assertEquals(1, team.getMembers().size());
        Assertions.assertEquals(person.getName(), team.getMembers().get(0).getName());
    }
}
