package com.practice.projectone.teammanagement.commands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.exceptions.ElementNotFoundException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class AddCommentToTaskCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 3;

    private TaskManagementSystemRepository taskManagementSystemRepository;
    private Command addCommentToTaskCommand;
    private List<String> validParams;

    @BeforeEach
    void beforeEach() {
        taskManagementSystemRepository = new TaskManagementSystemImpl();
        validParams = List.of(VALID_DESCRIPTION, VALID_AUTHOR_NAME, "1");
        addCommentToTaskCommand = new AddCommentToTaskCommand(taskManagementSystemRepository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> addCommentToTaskCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThroughException_When_IDNotNumber() {
        List<String> notValidParams = List.of(VALID_DESCRIPTION, VALID_AUTHOR_NAME, "NotValidID");

        Assertions.assertThrows(IllegalArgumentException.class, () -> addCommentToTaskCommand.execute(notValidParams));
    }

    @Test
    public void execute_Should_ThrowException_When_TaskDoesNotExist() {
        Assertions.assertThrows(ElementNotFoundException.class, () -> addCommentToTaskCommand.execute(validParams));
    }

    @Test
    public void execute_Should_AddCommentToTask_When_ParametersAreValid() {
        Feedback feedback = taskManagementSystemRepository
                .createFeedback(VALID_TITLE, VALID_DESCRIPTION, VALID_RATING);

        addCommentToTaskCommand.execute(validParams);

        Assertions.assertEquals(1, feedback.getComments().size());
    }
}
