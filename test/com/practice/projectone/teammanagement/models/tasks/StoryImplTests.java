package com.practice.projectone.teammanagement.models.tasks;

import com.practice.projectone.teammanagement.models.tasks.contracts.Story;
import com.practice.projectone.teammanagement.models.tasks.contracts.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.practice.projectone.teammanagement.utils.Constants.*;

public class StoryImplTests {

    @Test
    public void carImpl_Should_ImplementCarInterface() {
        StoryImpl story = initializeTestStory();

        Assertions.assertTrue(story instanceof Story);
    }

    @Test
    public void carImpl_Should_ImplementVehicleInterface() {
        StoryImpl story = initializeTestStory();

        Assertions.assertTrue(story instanceof Task);
    }

    @Test
    public void constructor_Should_ThrowException_When_StoryTitleLengthOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(
                        INVALID_TITLE_MIN,
                        VALID_DESCRIPTION,
                        VALID_SPECIFIC_TASK_PRIORITY,
                        VALID_STORY_SIZE));
    }

    @Test
    public void constructor_Should_ThrowException_When_StoryDescriptionLengthOutOfBounds() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                new StoryImpl(
                        VALID_TITLE,
                        INVALID_DESCRIPTION_MIN,
                        VALID_SPECIFIC_TASK_PRIORITY,
                        VALID_STORY_SIZE));
    }


    public StoryImpl initializeTestStory() {
        return new StoryImpl(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_SPECIFIC_TASK_PRIORITY,
                VALID_STORY_SIZE);
    }
}
