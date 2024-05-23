package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.enums.Severity;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ChangeSeverityCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final Severity VALID_SEVERITY_CHANGE = Severity.MINOR;
    private TaskManagementSystemRepository repository;
    private Command changeSeverityCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        changeSeverityCommand = new ChangeSeverityCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected(){
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeSeverityCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TaskIDIsNotANumber(){
        List<String> params = List.of("INVALID ID", VALID_SEVERITY_CHANGE.toString());
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeSeverityCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_SeverityIsInvalid(){
        List<String> params = List.of("1", "INVALID SEVERITY");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeSeverityCommand.execute(params)
        );
    }

    @Test
    public void should_ChangeSeverity_When_InputIsValid(){
        Bug bug = repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );

        List<String> params = List.of(String.valueOf(bug.getId()), VALID_SEVERITY_CHANGE.toString());
        changeSeverityCommand.execute(params);
        Assertions.assertEquals(VALID_SEVERITY_CHANGE, bug.getSeverity());
    }
}
