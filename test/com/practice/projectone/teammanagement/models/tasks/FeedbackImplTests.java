package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class FeedbackImplTests {

    private FeedbackImpl feedback;

    @BeforeEach
    void beforeEach() {
        feedback = new FeedbackImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING);
    }

    @Test
    public void FeedbackImpl_Should_ImplementFeedbackInterface() {
        Assertions.assertTrue(feedback instanceof Feedback);
    }

    @Test
    public void FeedbackImpl_Should_ImplementTaskInterface() {
        Assertions.assertTrue(feedback instanceof Task);
    }

    @Test
    public void constructor_Should_ThrowException_When_FeedbackRatingIsInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new FeedbackImpl(
                        VALID_TITLE,
                        VALID_DESCRIPTION,
                        INVALID_RATING)
        );
    }

    @Test
    public void constructor_Should_CreateNewFeedback_When_ParametersAreCorrect() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, feedback.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, feedback.getDescription()),
                () -> Assertions.assertEquals(VALID_RATING, feedback.getRating()),
                () -> Assertions.assertDoesNotThrow(() -> feedback.getComments()),
                () -> Assertions.assertDoesNotThrow(() -> feedback.getActivityHistory())
        );
    }

    @Test
    public void changeRating_Should_ThrowException_When_SameRating() {
        Assertions.assertThrows(InvalidUserInputException.class, () -> feedback.changeRating(VALID_RATING));
    }

    @Test
    public void changeRating_Should_ThrowException_When_InvalidRating() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.changeRating(INVALID_RATING));
    }

    @Test
    public void changeRating_Should_SetRating_When_ValidRating() {
        int newRating = VALID_RATING + 1;

        feedback.changeRating(newRating);

        Assertions.assertEquals(newRating, feedback.getRating());
    }

    @Test
    public void changeRating_Should_LogEvent_When_RatingIsChanged() {
        int newRating = VALID_RATING + 1;

        feedback.changeRating(newRating);

        Assertions.assertEquals(2, feedback.getActivityHistory().size());
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("Task ID%d%n  #Type: %s%n  #Title: %s%n  #Description: %s%n  #Status: %s%n  #Rating: %d%n",
                feedback.getId(), feedback.getTaskType(), feedback.getName(),
                feedback.getDescription(), feedback.getStatus(), feedback.getRating()).trim();

        Assertions.assertEquals(expected, feedback.toString());
    }
}
