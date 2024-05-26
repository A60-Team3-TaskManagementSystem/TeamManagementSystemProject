package com.practice.projectone.teammanagement.commands.showcommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.contracts.Person;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.VALID_NAME;

public class ShowPersonActivityLogCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 1;
    private TaskManagementSystemRepository repository;
    private Command showPersonActivity;

    @BeforeEach
    void beforeEach() {
        repository = new TaskManagementSystemImpl();
        showPersonActivity = new ShowPersonActivityLog(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> showPersonActivity.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_PersonDoesNotExists() {
        List<String> notValidParams = List.of(VALID_NAME);

        Assertions.assertThrows(ElementNotFoundException.class, () -> showPersonActivity.execute(notValidParams));
    }

    @Test
    public void execute_Should_ReturnPersonActivity_When_InputIsValid() {
        Person person = repository.createPerson(VALID_NAME);
        repository.addPerson(person);

        List<String> validParams = List.of(VALID_NAME);

        String execute = showPersonActivity.execute(validParams);

        Assertions.assertEquals(4, execute.split(System.lineSeparator()).length);
    }
}
