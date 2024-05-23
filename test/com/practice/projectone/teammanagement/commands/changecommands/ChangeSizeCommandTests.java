package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ChangeSizeCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final Size VALID_SIZE_CHANGE = Size.SMALL;

    private TaskManagementSystemRepository repository;
    private Command changeSizeCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        changeSizeCommand = new ChangeSizeCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected(){
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeSizeCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TaskIDIsNotANumber(){
        List<String> params = List.of("INVALID ID", VALID_SIZE_CHANGE.toString());
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeSizeCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_SizeIsInvalid(){
        List<String> params = List.of("1", "INVALID SIZE");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeSizeCommand.execute(params)
        );
    }

    @Test
    public void should_ChangeSize_When_InputIsValid(){
        Story story = repository.createStory(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        List<String> params = List.of(String.valueOf(story.getId()), VALID_SIZE_CHANGE.toString());
        changeSizeCommand.execute(params);
        Assertions.assertEquals(VALID_SIZE_CHANGE, story.getSize());
    }
}
