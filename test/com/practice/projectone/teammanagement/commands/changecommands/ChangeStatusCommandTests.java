package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Bug;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ChangeStatusCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final Status VALID_STATUS_CHANGE = Status.DONE;

    private TaskManagementSystemRepository repository;
    private Command changeStatusCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        changeStatusCommand = new ChangeStatusCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected(){
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeStatusCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TaskIDIsNotANumber(){
        List<String> params = List.of("INVALID ID", VALID_STATUS_CHANGE.toString());
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeStatusCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_StatusIsInvalid(){
        List<String> params = List.of("1", "INVALID STATUS");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeStatusCommand.execute(params)
        );
    }

    @Test
    public void should_ChangeStatus_When_InputIsValid(){
        repository.createBug(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        Bug bug = repository.findBugByID(1);
        List<String> params = List.of("1", VALID_STATUS_CHANGE.toString());
        changeStatusCommand.execute(params);
        Assertions.assertEquals(VALID_STATUS_CHANGE, bug.getStatus());
    }
}
