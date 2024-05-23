package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Priority;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
import com.practice.projectone.teammanagement.models.tasks.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class StoryImplTests {

    private StoryImpl story;

    @BeforeEach
    void beforeEach(){
        story = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);
    }

    @Test
    public void StoryImpl_Should_ImplementStoryInterface() {
        Assertions.assertTrue(story instanceof Story);
    }

    @Test
    public void StoryImpl_Should_ImplementTaskInterface() {
        Assertions.assertTrue(story instanceof Task);
    }

    @Test
    public void constructor_Should_ThrowException_When_StoryTitleLengthOutOfBounds() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new StoryImpl(
                                INVALID_TITLE_MIN,
                                VALID_DESCRIPTION,
                                VALID_SPECIFIC_TASK_PRIORITY,
                                VALID_STORY_SIZE)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new StoryImpl(
                                INVALID_TITLE_MAX,
                                VALID_DESCRIPTION,
                                VALID_SPECIFIC_TASK_PRIORITY,
                                VALID_STORY_SIZE))
        );
    }

    @Test
    public void constructor_Should_ThrowException_When_StoryDescriptionLengthOutOfBounds() {
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new StoryImpl(
                                VALID_TITLE,
                                INVALID_DESCRIPTION_MIN,
                                VALID_SPECIFIC_TASK_PRIORITY,
                                VALID_STORY_SIZE)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () ->
                        new StoryImpl(
                                VALID_TITLE,
                                INVALID_DESCRIPTION_MAX,
                                VALID_SPECIFIC_TASK_PRIORITY,
                                VALID_STORY_SIZE))
        );
    }

    @Test
    public void constructor_Should_CreateNewStory_When_ParametersAreCorrect() {
        Assertions.assertEquals(VALID_TITLE, story.getName());
    }

    @Test
    public void constructor_Should_LogEvent_When_StoryIsCreated() {
        Assertions.assertEquals(1, story.getActivityHistory().size());
    }
    @Test
    public void changeStatus_Should_ThrowException_When_SameStatus() {
        Status initialStoryStatus = Status.NOT_DONE;

        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeStatus(initialStoryStatus));
    }

    @Test
    public void changeStatus_Should_ThrowException_When_NotValidStoryStatus() {
        Status notValidStoryStatus = Status.NEW;

        Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeStatus(notValidStoryStatus));
    }

    @Test
    public void changeStatus_Should_SetStatus_When_NewStatusIsValid() {
        Status validStatus = Status.IN_PROGRESS;

        story.changeStatus(validStatus);

        Assertions.assertEquals(validStatus, story.getStatus());
    }

    @Test
    public void changeStatus_Should_LogEvent_When_StatusIsChanged() {
        Status validStatus = Status.IN_PROGRESS;

        story.changeStatus(validStatus);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void getAssignee_Should_ReturnNone_IfTaskIsYetAssigned() {
        String expected = "Not assigned yet";

        Assertions.assertEquals(expected, story.getAssignee());
    }

    @Test
    public void changeAssignee_Should_ThrowException_When_UnAssigningNotYetAssignedTask() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeAssignee(null));
    }

    @Test
    public void changeAssignee_Should_ThrowException_When_AssigningSamePerson() {
        String expected = "Pesho";

        story.changeAssignee(expected);

        Assertions.assertThrows(IllegalArgumentException.class, () -> story.changeAssignee(expected));
    }

    @Test
    public void changeAssignee_Should_AssignNewPerson_When_AssigningNotYetAssignedTask() {
        String expected = "Pesho";

        story.changeAssignee(expected);

        Assertions.assertEquals(expected, story.getAssignee());
    }

    @Test
    public void changeAssignee_Should_UnAssignPerson_When_PassedNullValue() {
        String assignee = "Pesho";
        String expected = "Not assigned yet";

        story.changeAssignee(assignee);
        story.changeAssignee(null);

        Assertions.assertEquals(expected, story.getAssignee());
    }

    @Test
    public void changeAssignee_Should_LogEvent_When_UnAssigningPerson() {
        String expected = "Pesho";

        story.changeAssignee(expected);
        story.changeAssignee(null);

        Assertions.assertEquals(3, story.getActivityHistory().size());
    }

    @Test
    public void changeAssignee_Should_LogEvent_When_AssigningNewPerson() {
        String expected = "Pesho";

        story.changeAssignee(expected);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void changeAssignee_Should_LogEvent_When_ChangingAssignee() {
        String assignee = "Gosho";
        String expected = "Pesho";

        story.changeAssignee(assignee);
        story.changeAssignee(expected);

        Assertions.assertEquals(3, story.getActivityHistory().size());
    }

    @Test
    public void changePriority_Should_ThrowException_When_SamePriority() {
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changePriority(VALID_SPECIFIC_TASK_PRIORITY));
    }

    @Test
    public void changePriority_Should_SetPriority_When_NewPriorityIsValid() {
        story.changePriority(Priority.LOW);

        Assertions.assertEquals(Priority.LOW, story.getPriority());
    }

    @Test
    public void changePriority_Should_LogEvent_When_PriorityIsChanged() {
        story.changePriority(Priority.LOW);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void changeSize_Should_ThrowException_When_NewSizeEqualsStorySize() {
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeSize(VALID_STORY_SIZE));
    }

    @Test
    void changeSize_Should_ChangeSize_When_NewSizeIsValid() {
        story.changeSize(Size.LARGE);

        Assertions.assertEquals(Size.LARGE, story.getSize());
    }

    @Test
    public void changeSize_Should_LogEvent_When_SizeIsChanged() {
        story.changeSize(Size.LARGE);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void validateStatus_Should_ThrowException_When_StatusProvidedIsNotValidForStory() {
        Status notValidStoryStatus = Status.NEW;

        Assertions.assertThrows(IllegalArgumentException.class, () -> story.validateStatus(notValidStoryStatus));
    }

    @Test
    public void getTaskType_Should_ReturnTaskName_When_Invoked() {
        String expected = "Story";

        Assertions.assertEquals(expected, story.getTaskType());
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("Task ID%d%n  #Type: %s%n  #Title: %s%n  #Description: %s%n  #Status: %s%n  #Priority: %s%n  #Size: %s%n  #AssignedTo: %s%n",
                story.getId(), story.getTaskType(), story.getName(),
                story.getDescription(), story.getStatus(), story.getPriority(),
                story.getSize(), story.getAssignee());

        Assertions.assertEquals(expected, story.toString());
    }

    @Test
    public void equals_Should_AssertEquality() {
        StoryImpl sameStory = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);;


        Assertions.assertAll(
                () -> Assertions.assertNotEquals(story, sameStory),
                () -> Assertions.assertEquals(story, story)
        );
    }

    @Test
    public void hashCode_Should_AssertEquality() {
        StoryImpl sameStory = new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(story.hashCode(), sameStory.hashCode()),
                () -> Assertions.assertEquals(story.hashCode(), story.hashCode())
        );
    }
}
