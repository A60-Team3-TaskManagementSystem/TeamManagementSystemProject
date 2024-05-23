package com.practice.projectone.teammanagement.commands.createcommands;

import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class CreatePersonTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private CreatePersonCommand createPersonCommand;
    private TaskManagementSystemRepository taskManagementSystemRepository;
    List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        createPersonCommand = new CreatePersonCommand(taskManagementSystemRepository);
        validParams = List.of(VALID_NAME);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountIsDifferentThanExpected() {
        List<String> parameters = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createPersonCommand.execute(parameters));
    }

    @Test
    public void execute_Should_ThrowException_When_PersonAlreadyExist() {
        createPersonCommand.execute(validParams);

        Assertions.assertThrows(IllegalArgumentException.class, () -> createPersonCommand.execute(validParams));
    }

    @Test
    public void execute_Should_CreatePerson_When_ParametersAreValid() {
        Person person = taskManagementSystemRepository.createPerson(VALID_NAME);

        createPersonCommand.execute(validParams);

        Assertions.assertAll(
                () -> Assertions.assertTrue(taskManagementSystemRepository.getMembers().contains(person)),
                () -> Assertions.assertEquals(person, taskManagementSystemRepository.findPersonByName(person.getName()))
        );
    }
}
