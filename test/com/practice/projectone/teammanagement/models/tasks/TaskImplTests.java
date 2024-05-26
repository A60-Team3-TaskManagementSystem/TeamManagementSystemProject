package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.CommentImpl;
import com.practice.projectone.teammanagement.models.contracts.Comment;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class TaskImplTests {

    private Task bug;
    private Task story;
    private Task feedback;

    @BeforeEach
    void setup() {
        bug = new BugImpl(
                VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_SEVERITY,
                VALID_STEPS_LIST
        );
        story = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE
        );
        feedback = new FeedbackImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_RATING
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_TaskTitleLengthOutOfBounds() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class, () ->
                                new FeedbackImpl(
                                        INVALID_TITLE_MIN,
                                        VALID_DESCRIPTION,
                                        VALID_RATING)
                ),
                () -> Assertions.assertThrows(
                        IllegalArgumentException.class, () ->
                        new FeedbackImpl(
                                INVALID_TITLE_MAX,
                                VALID_DESCRIPTION,
                                VALID_RATING)
                )
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_TaskDescriptionLengthOutOfBounds() {
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
    public void constructor_Should_LogEvent_When_TaskIsCreated() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, feedback.getActivityHistory().size()),
                () -> Assertions.assertEquals(1, bug.getActivityHistory().size()),
                () -> Assertions.assertEquals(1, story.getActivityHistory().size())
        );
    }

    @Test
    public void changeStatus_Should_ThrowException_When_SameStatus() {
        Status initialStoryStatus = Status.NOT_DONE;
        Status initialBugStatus = Status.ACTIVE;
        Status initialFeedbackStatus = Status.NEW;

        Assertions.assertAll(
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeStatus(initialStoryStatus)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> bug.changeStatus(initialBugStatus)),
                () -> Assertions.assertThrows(InvalidUserInputException.class, () -> feedback.changeStatus(initialFeedbackStatus))
        );

    }

    @Test
    public void changeStatus_Should_ThrowException_When_NotValidTaskStatus() {
        Status invalidStoryStatus = Status.NEW;
        Status invalidFeedbackStatus = Status.ACTIVE;
        Status invalidBugStatus = Status.IN_PROGRESS;

        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeStatus(invalidStoryStatus)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> bug.changeStatus(invalidBugStatus)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> feedback.changeStatus(invalidFeedbackStatus))
        );
    }

    @Test
    public void changeStatus_Should_SetStatus_When_NewStatusIsValid() {
        Status validStoryStatus = Status.IN_PROGRESS;
        Status validBugStatus = Status.DONE;
        Status validFeedbackStatus = Status.SCHEDULED;

        story.changeStatus(validStoryStatus);
        bug.changeStatus(validBugStatus);
        feedback.changeStatus(validFeedbackStatus);

        Assertions.assertAll(
                () -> Assertions.assertEquals(validStoryStatus, story.getStatus()),
                () -> Assertions.assertEquals(validBugStatus, bug.getStatus()),
                () -> Assertions.assertEquals(validFeedbackStatus, feedback.getStatus())
        );
    }

    @Test
    public void changeStatus_Should_LogEvent_When_StatusIsChanged() {
        Status validStoryStatus = Status.IN_PROGRESS;
        Status validBugStatus = Status.DONE;
        Status validFeedbackStatus = Status.SCHEDULED;

        story.changeStatus(validStoryStatus);
        bug.changeStatus(validBugStatus);
        feedback.changeStatus(validFeedbackStatus);

        Assertions.assertAll(
                () -> Assertions.assertEquals(2, story.getActivityHistory().size()),
                () -> Assertions.assertEquals(2, bug.getActivityHistory().size()),
                () -> Assertions.assertEquals(2, feedback.getActivityHistory().size())
        );
    }


    @Test
    public void getActivityHistory_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(feedback.getActivityHistory(), feedback.getActivityHistory());
    }

    @Test
    public void getComments_Should_ReturnCopyOfCollection() {
        Assertions.assertNotSame(story.getComments(), story.getComments());
    }

    @Test
    public void addComment_Should_AddCommentToTask() {
        Comment comment = new CommentImpl(VALID_AUTHOR_NAME, VALID_DESCRIPTION);

        feedback.addComment(comment);

        Assertions.assertEquals(1, feedback.getComments().size());
    }

    @Test
    public void addComment_Should_LogEvent_When_Invoked() {
        Comment comment = new CommentImpl(VALID_AUTHOR_NAME, VALID_DESCRIPTION);

        story.addComment(comment);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void getTaskType_Should_ReturnTaskName_When_Invoked() {
        String expectedStory = "Story";
        String expectedBug = "Bug";
        String expectedFeedback = "Feedback";

        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedFeedback, feedback.getTaskType()),
                () -> Assertions.assertEquals(expectedBug, bug.getTaskType()),
                () -> Assertions.assertEquals(expectedStory, story.getTaskType())
        );
    }

    @Test
    public void equals_Should_AssertEquality() {
        Task sameStory = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);
        ;


        Assertions.assertAll(
                () -> Assertions.assertNotEquals(story, sameStory),
                () -> Assertions.assertEquals(story, story)
        );
    }

    @Test
    public void hashCode_Should_AssertEquality() {
        Task sameStory = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(story.hashCode(), sameStory.hashCode()),
                () -> Assertions.assertEquals(story.hashCode(), story.hashCode())
        );
    }
}
