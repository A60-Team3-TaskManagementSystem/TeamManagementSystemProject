package com.practice.projectone.teammanagement.commands.changecommands;

import com.practice.projectone.teammanagement.commands.contracts.Command;
import com.practice.projectone.teammanagement.core.TaskManagementSystemImpl;
import com.practice.projectone.teammanagement.core.contracts.TaskManagementSystemRepository;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.utils.TestUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class ChangeRatingCommandTests {
    private static final int EXPECTED_PARAMETERS_COUNT = 2;
    private static final String VALID_RATING_CHANGE = "2";
    private TaskManagementSystemRepository repository;
    private Command changeRatingCommand;

    @BeforeEach
    public void beforeEach(){
        repository = new TaskManagementSystemImpl();
        changeRatingCommand = new ChangeRatingCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_ArgumentCountDifferentThanExpected(){
        List<String> params = TestUtilities.getList(EXPECTED_PARAMETERS_COUNT - 1);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeRatingCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_TaskIDIsNotANumber(){
        List<String> params = List.of("INVALID ID", VALID_RATING_CHANGE);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeRatingCommand.execute(params)
        );
    }

    @Test
    public void execute_Should_ThrowException_When_RatingIsInvalid(){
        List<String> params = List.of("1", "INVALID RATING");
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> changeRatingCommand.execute(params)
        );
    }

    @Test
    public void should_ChangeRating_When_InputIsValid(){
        Feedback feedback = repository.createFeedback(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
        List<String> params = List.of(String.valueOf(feedback.getId()), VALID_RATING_CHANGE);
        changeRatingCommand.execute(params);
        Assertions.assertEquals(Integer.parseInt(VALID_RATING_CHANGE), feedback.getRating());
    }
}
