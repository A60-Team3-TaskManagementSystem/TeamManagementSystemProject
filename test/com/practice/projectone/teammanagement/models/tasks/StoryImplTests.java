package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.exceptions.InvalidUserInputException;
import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import com.practice.projectone.teammanagement.models.tasks.enums.Size;
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
    public void constructor_Should_CreateNewStory_When_ParametersAreCorrect() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(VALID_TITLE, story.getName()),
                () -> Assertions.assertEquals(VALID_DESCRIPTION, story.getDescription()),
                () -> Assertions.assertEquals(VALID_SPECIFIC_TASK_PRIORITY, story.getPriority()),
                () -> Assertions.assertEquals(VALID_STORY_SIZE, story.getSize()),
                () -> Assertions.assertDoesNotThrow(() -> story.getComments()),
                () -> Assertions.assertDoesNotThrow(() -> story.getActivityHistory())
        );
    }

    @Test
    public void changeSize_Should_ThrowException_When_NewSizeEqualsStorySize() {
        Assertions.assertThrows(InvalidUserInputException.class, () -> story.changeSize(VALID_STORY_SIZE));
    }

    @Test
    public void changeSize_Should_ChangeSize_When_NewSizeIsValid() {
        story.changeSize(Size.LARGE);

        Assertions.assertEquals(Size.LARGE, story.getSize());
    }

    @Test
    public void changeSize_Should_LogEvent_When_SizeIsChanged() {
        story.changeSize(Size.LARGE);

        Assertions.assertEquals(2, story.getActivityHistory().size());
    }

    @Test
    public void toString_Should_PrintExpectedOutput() {
        String expected = String.format("Task ID%d%n  #Type: %s%n  #Title: %s%n  #Description: %s%n  #Status: %s%n  #Priority: %s%n  #Size: %s%n  #AssignedTo: %s%n",
                story.getId(), story.getTaskType(), story.getName(),
                story.getDescription(), story.getStatus(), story.getPriority(),
                story.getSize(), story.getAssignee());

        Assertions.assertEquals(expected, story.toString());
    }
}
