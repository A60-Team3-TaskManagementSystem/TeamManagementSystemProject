package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.CommentImpl;
import com.practice.projectone.teammanagement.models.tasks.contracts.Feedback;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class FeedbackImplTest {

    private FeedbackImpl feedback;

    @BeforeEach
    void beforeEach() {
        feedback = new FeedbackImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING);
    }

    @Test
    public void FeedbackImpl_Should_ImplementStoryInterface() {
        Assertions.assertTrue(feedback instanceof Feedback);
    }

    @Test
    public void FeedbackImpl_Should_ImplementTaskInterface() {
        Assertions.assertTrue(feedback instanceof Task);
    }

    @Test
    public void constructor_Should_ThrowException_When_FeedbackTitleLengthOutOfBounds() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class, () ->
                                new FeedbackImpl(
                                        INVALID_TITLE_MIN,
                                        VALID_DESCRIPTION,
                                        VALID_RATING)
                ),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new FeedbackImpl(
                                INVALID_TITLE_MAX,
                                VALID_DESCRIPTION,
                                VALID_RATING)
                )
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_FeedbackDescriptionLengthOutOfBounds() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new FeedbackImpl(
                                VALID_TITLE,
                                INVALID_DESCRIPTION_MIN,
                                VALID_RATING)
                ),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new FeedbackImpl(
                                VALID_TITLE,
                                INVALID_DESCRIPTION_MAX,
                                VALID_RATING)
                )
        );
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
        Assertions.assertEquals(VALID_TITLE, feedback.getName());
    }

    @Test
    public void constructor_Should_LogEvent_When_FeedbackIsCreated() {
        Assertions.assertEquals(1, feedback.getActivityHistory().size());
    }

    @Test
    public void changeStatus_Should_ThrowException_When_SameStatus() {
        Status initialFeedbackStatus = Status.NEW;

        Assertions.assertThrows(InvalidUserInputException.class, () -> feedback.changeStatus(initialFeedbackStatus));
    }

    @Test
    public void changeStatus_Should_ThrowException_When_NotValidFeedbackStatus() {
        Status notValidFeedbackStatus = Status.NOT_DONE;

        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.changeStatus(notValidFeedbackStatus));
    }

    @Test
    public void changeStatus_Should_SetStatus_When_NewStatusIsValid() {
        Status validStatus = Status.UNSCHEDULED;

        feedback.changeStatus(validStatus);

        Assertions.assertEquals(validStatus, feedback.getStatus());
    }

    @Test
    public void changeStatus_Should_LogEvent_When_StatusIsChanged() {
        Status validStatus = Status.SCHEDULED;

        feedback.changeStatus(validStatus);

        Assertions.assertEquals(2, feedback.getActivityHistory().size());
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
    public void validateStatus_Should_ThrowException_When_StatusProvidedIsNotValidForFeedback() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.validateStatus(Status.ACTIVE));
    }

    @Test
    public void getActivityHistory_Should_ReturnNewArrays_When_Invoked() {
        Assertions.assertNotSame(feedback.getActivityHistory(), feedback.getActivityHistory());
    }
    @Test
    public void getComments_Should_ReturnNewArray_When_Invoked(){
        Assertions.assertNotSame(feedback.getComments(), feedback.getComments());
    }

    @Test
    public void addComment_Should_AddCommentToTask(){
        CommentImpl comment = new CommentImpl("xxxxxx", "xxxx");

        feedback.addComment(comment);

        Assertions.assertEquals(1, feedback.getComments().size());
    }

    @Test
    public void addComment_Should_LogEvent_When_Invoked(){
        CommentImpl comment = new CommentImpl("xxxxxx", "xxxx");

        feedback.addComment(comment);

        Assertions.assertEquals(2, feedback.getActivityHistory().size());
    }

    @Test
    public void getTaskType_Should_ReturnTaskName_When_Invoked() {
        String expected = "Feedback";

        Assertions.assertEquals(expected, feedback.getTaskType());
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("Task ID%d%n  #Type: %s%n  #Title: %s%n  #Description: %s%n  #Status: %s%n  #Rating: %d%n",
                feedback.getId(), feedback.getTaskType(), feedback.getName(),
                feedback.getDescription(), feedback.getStatus(), feedback.getRating());

        Assertions.assertEquals(expected, feedback.toString());
    }
}
